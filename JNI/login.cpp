//
// Created by Machenike on 2020/4/17.
//
#include "SubwayJNI.h"

#define RESPONSE_DATA_LEN 4096

// 接收传出服务器返回 buffer
typedef struct login_response_data
{
    login_response_data(){
        memset(data, 0, RESPONSE_DATA_LEN);
        data_len = 0;
    }

    char data[RESPONSE_DATA_LEN];
    int data_len;
} response_data_t;

// 将服务器返回数据拷贝至arg中，重新定义函数处理
size_t response(void *ptr, size_t n, size_t m, void *arg)
{
    int count = m * n;
    response_data_t *response_data = (response_data_t *)arg;
    memcpy(response_data->data, ptr, count);
    response_data->data_len = count;
    return response_data->data_len;
}

#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_example_subwayinfo_SubwayJNI
 * Method:    login
 * Signature: (Ljava/lang/String;Ljava/lang/String;Z)Z
 */
JNIEXPORT jboolean JNICALL Java_com_example_subwayinfo_SubwayJNI_login
        (JNIEnv *env, jobject obj, jstring j_username, jstring j_password, jboolean j_regist)
{
    const char *username = env->GetStringUTFChars(j_username, 0);
    const char *password = env->GetStringUTFChars(j_password, 0);
    const char *regist = (j_regist == JNI_TRUE?"yes":"no");

    char *post_str = NULL;

    CURL *curl = NULL;
    CURLcode res;
    response_data_t responseData; //存放从服务器返回的数据

    curl = curl_easy_init();
    if(curl == NULL){
        __android_log_print(ANDROID_LOG_ERROR, TAG, "JNI-login: curl init error\n");
        return JNI_FALSE;
    }

    __android_log_print(ANDROID_LOG_ERROR, TAG, "JNI-login: username = %s, password = %s, regist = %s\n",
            username, password, regist);

    // json
    /*
    //发送至服务器 json
    https://ip:port/login [json_data]
    {
        username: "hsy",
        password: "123456",
        remember: "yes"
    }
    */

    //1.封装 JSON
    cJSON *root = cJSON_CreateObject();

    cJSON_AddStringToObject(root, "username", username);
    cJSON_AddStringToObject(root, "password", password);
    cJSON_AddStringToObject(root, "regist", regist);

    post_str = cJSON_Print(root);
    cJSON_Delete(root);
    root = NULL;

    __android_log_print(ANDROID_LOG_ERROR, TAG, "JNI-login: post_str = [%s]\n", post_str);

    //2.向服务器 POST 请求 http
    // curl url
    curl_easy_setopt(curl, CURLOPT_URL, "https://120.26.173.34:8888/login");

    // 客户端忽略CA证书认证
    curl_easy_setopt(curl, CURLOPT_SSL_VERIFYHOST, false);
    curl_easy_setopt(curl, CURLOPT_SSL_VERIFYPEER, false);
    // 开启 post
    curl_easy_setopt(curl, CURLOPT_POST, true);
    // 添加 post 数据
    curl_easy_setopt(curl, CURLOPT_POSTFIELDS, post_str);
    // 响应回调
    curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, response);
    // 回调函数传递形参
    curl_easy_setopt(curl, CURLOPT_WRITEDATA, &responseData);
    // 向服务器发送请求
    res = curl_easy_perform(curl);
    if(res != CURLE_OK){
        __android_log_print(ANDROID_LOG_ERROR, TAG, "JNI-login: perform ERROR, rescode = [%d]\n", res);
        return JNI_FALSE;
    }
    //3.等待server响应数据 ---> responseData

    /*
    成功
    {
        result: "ok",
        recode: "0",
        sessionid: "online-user-xxxx-xxx-xxx-xxxx",
        orderid:"NONE",
        status:"idle"
    }
    //失败
    {
        result: "error",
        reason: "why...."
    }
    */
    // 4.解析result
    // if(result == ok)登陆成功, else if(result == error) 登录失败
    root = cJSON_Parse(responseData.data);

    cJSON *result = cJSON_GetObjectItem(root, "result");
    if(result && strcmp(result->valuestring, "ok") == 0){
        // 成功
        __android_log_print(ANDROID_LOG_ERROR, TAG, "JNI-login: login Success!\n");
        return JNI_TRUE;
    } else{
        // 失败
        cJSON *reason = cJSON_GetObjectItem(root, "reason");
        if(reason){
            __android_log_print(ANDROID_LOG_ERROR, TAG, "JNI-login: login Erron!, reason = %s\n", reason->valuestring);
        } else{
            __android_log_print(ANDROID_LOG_ERROR, TAG, "JNI-login: login Erron!, reason = Unknow\n");
            return JNI_FALSE;
        }
    }
    return JNI_FALSE;
}
#ifdef __cplusplus
}
#endif