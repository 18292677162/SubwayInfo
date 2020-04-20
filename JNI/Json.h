//
// Created by Machenike on 2020/4/20.
//

#ifndef SUBWAYINFO_JSON_H
#define SUBWAYINFO_JSON_H

#include "SubwayJNI.h"
#include "cJSON.h"

using namespace std;

class Json {

public:
    Json();
    ~Json();

    //插入 key value(String)
    void insert(string key, string value);

    // json Object 转换为字符串
    string print();

    // json 解析为 json 对象
    void parse(string str_json);

    // 通过 KEY 得到 json 中的 value
    string value(string key);

private:
    // 防止浅拷贝
    Json(const Json &);
    Json &operator = (const Json &);

    cJSON *_root;
};


#endif //SUBWAYINFO_JSON_H
