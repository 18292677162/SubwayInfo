//
// Created by Machenike on 2020/4/17.
//
#include "SubwayJNI.h"

#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_example_subwayinfo_SubwayJNI
 * Method:    login
 * Signature: (Ljava/lang/String;Ljava/lang/String;Z)Z
 */
JNIEXPORT jboolean JNICALL Java_com_example_subwayinfo_SubwayJNI_login
        (JNIEnv *env, jobject obj, jstring j_username, jstring j_passwd, jboolean j_remember)
{
    const char *username = env->GetStringUTFChars(j_username, 0);
    const char *passwd = env->GetStringUTFChars(j_passwd, 0);
    const char *remember = (j_remember == JNI_TRUE?"yes":"no");

    __android_log_print(ANDROID_LOG_ERROR, TAG, "JNI-login: username = %s, passwd = %s, remember = %s",
            username, passwd, remember);

    // json
    /*
    //发送至服务器 json
    https://ip:port/login [json_data]
    {
        username: "hsy",
        password: "123456",
        remember: "yes"
    }

    //1.封装 JSON
    //2.向服务器 POST 请求 http
    //3.响应数据
    //成功
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
    // 解析result
    // if(result == ok)登陆成功, else if(result == error) 登录失败


    */
    return JNI_TRUE;
}
#ifdef __cplusplus
}
#endif