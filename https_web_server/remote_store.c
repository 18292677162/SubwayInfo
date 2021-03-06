#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <limits.h>

#include <sys/stat.h>
#include <sys/socket.h>
#include <fcntl.h>
#include <unistd.h>
#include <dirent.h>
#include <cJSON.h>
#include <curl/curl.h>
#include "remote_store.h"
#include "util.h"




static size_t reg_curl_to_dataserver_cb(char* ptr, size_t n, size_t m, void* userdata) 
{
    curl_response_data_t *data = (curl_response_data_t*)userdata;

    int response_data_len = n*m;

    memcpy(data->data+data->data_len, ptr, response_data_len);
    data->data_len+=response_data_len;

    return response_data_len;

}




static int curl_send(const char *uri, const char* request_json_str, curl_response_data_t *res_data, int ignoreCA)
{

    //curl --> data server
    CURL *curl = curl_easy_init();

    if (ignoreCA == 1) {
        //忽略https CA认证
        curl_easy_setopt(curl, CURLOPT_SSL_VERIFYPEER, 0);
        curl_easy_setopt(curl, CURLOPT_SSL_VERIFYHOST, 0);
    }

    //设置post数据
    curl_easy_setopt(curl, CURLOPT_URL, uri);
    curl_easy_setopt(curl, CURLOPT_POST, 1);
    curl_easy_setopt(curl, CURLOPT_POSTFIELDS, request_json_str);


    //设置回调函数
    curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, reg_curl_to_dataserver_cb);
    curl_easy_setopt(curl, CURLOPT_WRITEDATA, res_data);

    //提交请求给数据服务器
    CURLcode code = curl_easy_perform(curl);
    if (code != CURLE_OK) {
        LOG(LOG_MODULE, LOG_PROC_REMOTE_CURL, "curl easy perform error code = %d\n", code);
        return -1;
    }

    res_data->data[res_data->data_len] = '\0';

    curl_easy_cleanup(curl);

    return 0;
}


/* -------------------------------------------*/
/**
 * @brief  远程查询登陆信息表
 *
 * @param username
 * @param password
 *
 * @returns   
 *  0 succ  -1 fail
 */
/* -------------------------------------------*/
int curl_to_dataserver_login(const char *username, const char *password)
{
    int ret = 0;

    cJSON* request_json = cJSON_CreateObject();
    cJSON_AddStringToObject(request_json, "cmd", "query");
    cJSON_AddStringToObject(request_json, "remote_store", "login");
    cJSON_AddStringToObject(request_json, "table", "SUBWAY_TABLE_USER");
    cJSON_AddStringToObject(request_json, "username", username);
    //TODO 将password进行md5加密
    cJSON_AddStringToObject(request_json, "password", password);


    char * request_json_str = cJSON_Print(request_json);
    curl_response_data_t res_data;
    memset(&res_data, 0, sizeof(res_data));

    if (curl_send(URI_DATA_SERVER_PER, request_json_str,  &res_data, 1) != 0) {
        ret = -1;
        LOG(LOG_MODULE, LOG_PROC_REMOTE_CURL, "curl_to_dataserver_login send error, %s, %s\n", username, password);
        goto END;
    }
    cJSON_Delete(request_json);
    free(request_json_str);

    cJSON *res_root = cJSON_Parse(res_data.data);

    cJSON *result = cJSON_GetObjectItem(res_root, "result");
    if (result && (strcmp(result->valuestring, "ok") == 0)) {
        //succ
        LOG(LOG_MODULE, LOG_PROC_REMOTE_CURL, "query login succ\n");
        ret = 0;
    }
    else {
        //fail
        cJSON *reason = cJSON_GetObjectItem(res_root, "reason");
        if (reason) {
            LOG(LOG_MODULE, LOG_PROC_REMOTE_CURL, "query login, reason = %s\n", reason->valuestring);
        }
        else {
            LOG(LOG_MODULE, LOG_PROC_REMOTE_CURL,"query  login, unknow reason, res_data=%s\n", res_data.data);
        }

        ret = -1;

    }
    cJSON_Delete(res_root);



END:
    return ret;
}




/* -------------------------------------------*/
/**
 * @brief  远程存储SESSIONID  临时
 *
 * @param username
 * @param sessionid 
 * @param orderid 
 *
 * @returns   
 *          0 succ, -1 fail
 */
/* -------------------------------------------*/
int curl_to_cacheserver_session(const char *username,  const char* sessionid, const char *orderid)
{
    int ret = 0;

    LOG(LOG_MODULE, LOG_PROC_REMOTE_CURL, "sessionid = %s, username  = %s\n", sessionid, username);

    cJSON* request_json = cJSON_CreateObject();
    cJSON_AddStringToObject(request_json, "cmd", "setHash");
    cJSON_AddStringToObject(request_json, "key", sessionid);

    cJSON* fields = cJSON_CreateArray();
    cJSON_AddItemToArray(fields, cJSON_CreateString("username"));
    cJSON_AddItemToArray(fields, cJSON_CreateString("orderid"));

    cJSON* values = cJSON_CreateArray();
    cJSON_AddItemToArray(values, cJSON_CreateString(username));
    cJSON_AddItemToArray(values, cJSON_CreateString(orderid));

    cJSON_AddItemToObject(request_json, "fields", fields);
    cJSON_AddItemToObject(request_json, "values", values);


    char * request_json_str = cJSON_Print(request_json);
    curl_response_data_t res_data;
    memset(&res_data, 0, sizeof(res_data));


    if (curl_send(URI_DATA_SERVER_CHE, request_json_str,  &res_data, 1) != 0) {
        ret = -1;
        goto END;
    }

    cJSON_Delete(request_json);
    free(request_json_str);


    cJSON *res_root = cJSON_Parse(res_data.data);

    cJSON *result = cJSON_GetObjectItem(res_root, "result");
    if (result && (strcmp(result->valuestring, "ok") == 0)) {
        //succ
        LOG(LOG_MODULE, LOG_PROC_REMOTE_CURL, "set session  succ\n");
    }
    else {
        //fail
        cJSON *reason = cJSON_GetObjectItem(res_root, "reason");
        if (reason) {
            LOG(LOG_MODULE, LOG_PROC_REMOTE_CURL, "set session error, reason = %s\n", reason->valuestring);
        }
        else {
            LOG(LOG_MODULE, LOG_PROC_REMOTE_CURL, "set session error, unknow reason, res_data=%s\n", res_data.data);
        }

        ret = -1;

    }
    cJSON_Delete(res_root);


END:
    return ret;
}




/* -------------------------------------------*/
/**
 * @brief   远程设置一个key的超时时间(生命周期)
 *
 * @param sessionid
 * @param lifecycle
 *
 * @returns   
 */
/* -------------------------------------------*/
int curl_to_cacheserver_lifecycle(const char *sessionid, int lifecycle)
{
    int ret = 0;

    cJSON* request_json = cJSON_CreateObject();
    cJSON_AddStringToObject(request_json, "cmd", "setLifecycle");
    cJSON_AddStringToObject(request_json, "key", sessionid);
    cJSON_AddNumberToObject(request_json, "lifecycle", lifecycle);


    char * request_json_str = cJSON_Print(request_json);
    curl_response_data_t res_data;
    memset(&res_data, 0, sizeof(res_data));

    if (curl_send(URI_DATA_SERVER_CHE, request_json_str,  &res_data, 1) != 0) {
        ret = -1;
        LOG(LOG_MODULE, LOG_PROC_REMOTE_CURL, "curl send error\n");
        goto END;
    }
    cJSON_Delete(request_json);
    free(request_json_str);



    cJSON *res_root = cJSON_Parse(res_data.data);

    cJSON *result = cJSON_GetObjectItem(res_root, "result");
    if (result && (strcmp(result->valuestring, "ok") == 0)) {
        //succ
        LOG(LOG_MODULE, LOG_PROC_REMOTE_CURL, "set lifecycle  succ\n");
    }
    else {
        //fail
        cJSON *reason = cJSON_GetObjectItem(res_root, "reason");
        if (reason) {
            LOG(LOG_MODULE, LOG_PROC_REMOTE_CURL, "set lifecycle error, reason = %s\n", reason->valuestring);
        }
        else {
            LOG(LOG_MODULE, LOG_PROC_REMOTE_CURL, "set lifecycle error, unknow reason, res_data=%s\n", res_data.data);
        }

        ret = -1;

    }
    cJSON_Delete(res_root);

END:
    return ret;
}



/* -------------------------------------------*/
/**
 * @brief  远程注册用户请求业务
 *
 * @param username
 * @param password      
 *
 * @returns   
 *              0 succ, -1 fail
 */
/* -------------------------------------------*/
int curl_to_dataserver_reg(const char* username, 
        const char* password)
{
    int ret = 0;


    cJSON* request_json = cJSON_CreateObject();
    cJSON_AddStringToObject(request_json, "cmd", "insert");
    cJSON_AddStringToObject(request_json, "table", "SUBWAY_TABLE_USER");
    cJSON_AddStringToObject(request_json, "remote_store", "reg");
    cJSON_AddStringToObject(request_json, "username", username);
    //TODO 将password进行md5加密
    cJSON_AddStringToObject(request_json, "password", password);


    char * request_json_str = cJSON_Print(request_json);
    curl_response_data_t res_data;
    memset(&res_data, 0, sizeof(res_data));


    if (curl_send(URI_DATA_SERVER_PER,request_json_str,  &res_data, 1) != 0) {
        ret = -1;
        LOG(LOG_MODULE, LOG_PROC_REMOTE_CURL, "curl send error\n");
        goto END;
    }
    cJSON_Delete(request_json);

    free(request_json_str);


    cJSON *res_root = cJSON_Parse(res_data.data);

    cJSON *result = cJSON_GetObjectItem(res_root, "result");
    if (result && (strcmp(result->valuestring, "ok") == 0)) {
        //succ
        LOG(LOG_MODULE, LOG_PROC_REMOTE_CURL, "store reg succ\n");
    }
    else {
        //fail
        cJSON *reason = cJSON_GetObjectItem(res_root, "reason");
        if (reason) {
            LOG(LOG_MODULE, LOG_PROC_REMOTE_CURL, "reg store error, reason = %s\n", reason->valuestring);
        }
        else {
            LOG(LOG_MODULE, LOG_PROC_REMOTE_CURL, "reg store  error, unknow reason, res_data=%s\n", res_data.data);
        }

        ret = -1;
    }
    cJSON_Delete(res_root);


END:
    return ret;
}


