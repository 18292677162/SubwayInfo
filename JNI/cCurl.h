//
// Created by Pinna on 2020/4/21.
//

#ifndef SUBWAYINFO_CCURL_H
#define SUBWAYINFO_CCURL_H

#include "SubwayJNI.h"

using namespace std;

class cCurl {
public:
    cCurl(string url, bool ingoreCA);
    ~cCurl();

    // 向远程服务器发送请求
    bool send_post(string requestData);

    // 回调函数 函数参数默认传递一个 CURL *const this 指针 所以写成静态
    static size_t response(void *ptr, size_t m, size_t n, void *arg);

    // 获取服务器返回数据
    string responseData();
private:
    CURL *_curl;
    string _responseData; //存储服务器返回数据
};


#endif //SUBWAYINFO_CCURL_H
