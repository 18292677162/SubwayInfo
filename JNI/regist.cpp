//
// Created by Machenike on 2020/4/20.
//
#include "SubwayJNI.h"
/*
 * Class:     com_example_subwayinfo_SubwayJNI
 * Method:    regist
 * Signature: (Ljava/lang/String;Ljava/lang/String;)Z
 */
#ifdef __cplusplus
extern "C" {
#endif
JNIEXPORT jboolean JNICALL Java_com_example_subwayinfo_SubwayJNI_regist
        (JNIEnv *env, jobject obj, jstring j_username, jstring j_password)
{
    const char *username = env->GetStringUTFChars(j_username, NULL);
    const char *password = env->GetStringUTFChars(j_password, NULL);
    /*
    ==== 给服务端的协议 ====
    https://ip:port/regist [json_data]
    {
        username: "gailun",
        password: "123123",
    }
    */
    // 封装用户 json 字符串
    Json json;

    json.insert("username", username);
    json.insert("password", password);

    string json_str = json.print();
    __android_log_print(ANDROID_LOG_ERROR, TAG, "JNI-regist: post_str = [%s]\n", json_str.c_str());

    // 释放资源
    env->ReleaseStringUTFChars(j_username, username);
    env->ReleaseStringUTFChars(j_password, password);

    // 向服务器发送请求
    cCurl curl("http://120.26.173.34:8888/regist");

    if(curl.send_post(json_str)){
        __android_log_print(ANDROID_LOG_ERROR, TAG, "JNI-regist: send_post Error!\n");
        return JNI_FALSE;
    }

    // 服务器回复 json

    /*
    ====得到服务器响应数据 ====
    注册成功就默认为登陆状态
    //成功
    {
        result: "ok"
    }
    //失败
    {
        result: "error",
        reason: "why...."
    }
    */

    // 解析服务器返回的数据 curl.responseData();
    string responseData = curl.responseData();

    Json json_res;
    json_res.parse(responseData);

    string result = json_res.value("result");
    if(result == "ok"){
        // 注册成功
        __android_log_print(ANDROID_LOG_ERROR, TAG, "JNI-regist: regist Success!\n");
        return JNI_TRUE;
    } else{
        // 注册失败
        string reason = json_res.value("reason");
        if(reason == "")
            // 未知错误
            __android_log_print(ANDROID_LOG_ERROR, TAG, "JNI-regist: regist Fail! reason Unknow!\n");
        else
            __android_log_print(ANDROID_LOG_ERROR, TAG, "JNI-regist: regist Fail! reason = [%s]\n", reason.c_str());
    }

    return JNI_TRUE;
}
#ifdef __cplusplus
}
#endif