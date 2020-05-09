//
// Created by Pinna on 2020/4/21.
//
#include "cCurl.h"

cCurl::cCurl(string url, bool ingoreCA){
    this->_curl = curl_easy_init();
    // 设置url路径
    curl_easy_setopt(this->_curl, CURLOPT_URL, url.c_str());

    // 忽略 CA
    if(ingoreCA == true){
        curl_easy_setopt(this->_curl, CURLOPT_SSL_VERIFYHOST, false);
        curl_easy_setopt(this->_curl, CURLOPT_SSL_VERIFYPEER, false);
    }
}

cCurl::~cCurl()
{
    curl_easy_cleanup(this->_curl);
}

size_t cCurl::response(void *ptr, size_t m, size_t n, void *arg)
{
    cCurl *This = (cCurl *)arg;

    char *p = (char *)ptr;

    int count = m * n;

    /*
    for (int i = 0; i < count; ++i) {
        This->_responseData += p[i];
    }
    */

    //string copy
    copy(p, p + count, back_inserter(This->_responseData));

    return count;
}

string cCurl::responseData()
{
    return this->_responseData;
}

// 向远程服务器发送请求
bool cCurl::send_post(string requestData)
{
    CURLcode res;

    // 回调
    curl_easy_setopt(this->_curl, CURLOPT_POST, true);

    // post 数据
    curl_easy_setopt(this->_curl, CURLOPT_POSTFIELDS, requestData.c_str());

    // 回调
    curl_easy_setopt(this->_curl, CURLOPT_WRITEFUNCTION, response);
    curl_easy_setopt(this->_curl, CURLOPT_WRITEDATA, this);

    res = curl_easy_perform(this->_curl);
    if(res != CURLE_OK){
        __android_log_print(ANDROID_LOG_ERROR, TAG, "curl: perform ERROR, rescode = [%d]\n", res);
        return false;
    }
    return true;
}