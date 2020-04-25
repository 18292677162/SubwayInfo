#include "https-common.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <limits.h>

#include <sys/types.h>
#include <sys/stat.h>

#include <sys/stat.h>
#include <sys/socket.h>
#include <fcntl.h>
#include <unistd.h>
#include <dirent.h>

#include <openssl/ssl.h>
#include <openssl/err.h>

#include <event2/bufferevent.h>
#include <event2/bufferevent_ssl.h>
#include <event2/event.h>
#include <event2/http.h>
#include <event2/buffer.h>
#include <event2/util.h>
#include <event2/keyvalq_struct.h>

#include <cJSON.h>
#include "util.h"

void regist_cb (struct evhttp_request *req, void *arg)
{ 
    struct evbuffer *evb = NULL;
    const char *uri = evhttp_request_get_uri (req);
    struct evhttp_uri *decoded = NULL;

    /* 判断 req 是否是GET 请求 */
    if (evhttp_request_get_command (req) == EVHTTP_REQ_GET)
    {
        struct evbuffer *buf = evbuffer_new();
        if (buf == NULL) return;
        evbuffer_add_printf(buf, "Requested: %s\n", uri);
        evhttp_send_reply(req, HTTP_OK, "OK", buf);
        return;
    }

    /* 只处理Post请求, Get请求，就直接return 200 OK  */
    if (evhttp_request_get_command (req) != EVHTTP_REQ_POST)
    { 
        evhttp_send_reply (req, 200, "OK", NULL);
        return;
    }

    printf ("Got a POST request for <%s>\n", uri);

    //判断此URI是否合法
    decoded = evhttp_uri_parse (uri);
    if (! decoded)
    { 
        printf ("It's not a good URI. Sending BADREQUEST\n");
        evhttp_send_error (req, HTTP_BADREQUEST, 0);
        return;
    }

    /* Decode the payload */
    struct evbuffer *buf = evhttp_request_get_input_buffer (req);
    evbuffer_add (buf, "", 1);    /* NUL-terminate the buffer */
    char *payload = (char *) evbuffer_pullup (buf, -1);
    int post_data_len = evbuffer_get_length(buf);
    char request_data_buf[4096] = {0};
    memcpy(request_data_buf, payload, post_data_len);

    printf("[post_data][%d]=\n %s\n", post_data_len, payload);


    /*
       具体的：可以根据Post的参数执行相应操作，然后将结果输出
       ...
    */

    int is_regist_success = 0; //0-succ 1-fail
    //unpack json
    cJSON* root = cJSON_Parse(request_data_buf);
    cJSON* username = cJSON_GetObjectItem(root, "username");
    cJSON* password = cJSON_GetObjectItem(root, "password");

    printf("username = %s\n", username->valuestring);
    printf("password = %s\n", password->valuestring);

    // 插入数据库
    /*
    ==== 给服务端的协议 ====   
    https://ip:port/regist [json_data]  
    {
        cmd: "insert",
        busi: "regist",
        table: "Subway_TABLE_USER",

        username: "hsy",
        password: "123456"
    }
    */
    // 发送给 data server
    cJSON *request_data_root = cJSON_CreateObject();
    cJSON_AddStringToObject(request_data_root, "cmd", "insert");
    cJSON_AddStringToObject(request_data_root, "busi", "regist");
    cJSON_AddStringToObject(request_data_root, "table", "Subway_TABLE_USER");
    cJSON_AddStringToObject(request_data_root, "username", username->valuestring);
    cJSON_AddStringToObject(request_data_root, "password", password->valuestring);
    
    char *regist_post_data = cJSON_Print(request_data_root);

    CURL *curl = NULL;
    CURLcode res;
    response_data_t response_server_data;


    curl = curl_easy_init();
    if(curl == NULL){
        printf("curl init error\n");
        // 返回错误给前端
    }

    curl_easy_setopt(curl, CURLOPT_URL, "https://120.26.173.34:8887/persistent");        
    curl_easy_setopt(curl, CURLOPT_SSL_VERIFYHOST, 0);
    curl_easy_setopt(curl, CURLOPT_SSL_VERIFYPEER, 0);

    curl_easy_setopt(curl, CURLOPT_POST, 1);
    curl_easy_setopt(curl, CURLOPT_POSTFIELDS, regist_post_data);

    // 回调
    curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, deal_response);
    curl_easy_setopt(curl, CURLOPT_WRITEDATA, &response_server_data);

    res = curl_easy_perform(curl);
    if(res != CURLE_OK){
        printf("curl to data server perform error res = %d\n", res);
        // 释放变量
        // 返回 json 前端
    }


    /*
    ====得到服务器响应数据 ====
     注册成功就默认为登陆状态

    //成功
    {
        result: "ok",
        recode: "0",

        sessionid: "online-driver-xxxx-xxx-xxx-xxxx",
        orderid:"NONE",
        status:"idle"
    }
    //失败
    {
        result: "error",
        reason: "why...."
    }
    */
    cJSON *response_from_data = cJSON_Parse(response_server_data.data);
    cJSON *result = cJSON_GetObjectItem(response_from_data, "result");
    if(result && strcmp(result->valuestring, "ok") == 0){
        // 入库成功
        printf("insert Subway_TABLE_USER Success!\n");
        is_regist_success = 0;
    }else{
        printf("insert Subway_TABLE_USER Fail!\n");
        is_regist_success = -1;
    }

    //生成一个sessionid
    char sessionid[SESSIONID_LEN] = {0};

    create_sessionid(sessionid);
    printf("sessionid = %s\n", sessionid);
    // 向存储服务器发送 serHash 指令 /cache 指令

    // 得到结果


    cJSON_Delete(root);

    //packet json
    root = cJSON_CreateObject();

    if(is_regist_success == 0)
        cJSON_AddStringToObject(root, "result", "ok");
    else if(is_regist_success == -1){
        cJSON_AddStringToObject(root, "result", "error");
        cJSON_AddStringToObject(root, "reason", "insert Subway_TABLE_USER Error\n");
    }

    //cJSON_AddStringToObject(root, "sessionid", "xxxxxxxx");

    char *response_data = cJSON_Print(root);
    cJSON_Delete(root);

    cJSON_Delete(request_data_root);

    /* This holds the content we're sending. */

    //HTTP header
    evhttp_add_header(evhttp_request_get_output_headers(req), "Server", MYHTTPD_SIGNATURE);
    evhttp_add_header(evhttp_request_get_output_headers(req), "Content-Type", "text/plain; charset=UTF-8");
    evhttp_add_header(evhttp_request_get_output_headers(req), "Connection", "close");

    evb = evbuffer_new ();
    evbuffer_add_printf(evb, "%s", response_data);
    //将封装好的evbuffer 发送给客户端
    evhttp_send_reply(req, HTTP_OK, "OK", evb);

    if (decoded)
        evhttp_uri_free (decoded);
    if (evb)
        evbuffer_free (evb);


    printf("[response]:\n");
    printf("%s\n", response_data);

    free(response_data);
}
