//
// Created by Pinna on 2020/4/20.
//

#include "Json.h"

Json::Json()
{
    this->_root = cJSON_CreateObject();
}

Json::~Json()
{
    cJSON_Delete(this->_root);
}

//插入 key value(String)
void Json::insert(string key, string value)
{
    cJSON_AddStringToObject(this->_root, key.c_str(), value.c_str());
}

// json Object 转换为字符串
string Json::print()
{
    return string(cJSON_Print(this->_root));
}

// json 解析为 json 对象
void Json::parse(string str_json)
{
    cJSON_Delete(this->_root);
    this->_root = cJSON_Parse(str_json.c_str());
}

// 通过 KEY 得到 json 中的 value
string Json::value(string key)
{
    cJSON *obj = cJSON_GetObjectItem(this->_root, key.c_str());

    if(NULL == obj)
        return string();

    return string(obj->valuestring);
}