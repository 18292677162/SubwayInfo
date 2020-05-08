#include "util.h"
#include "remote_store.h"
#include "util.h"

// curl 处理存储服务器返回数据的回调
size_t deal_response(void *ptr, size_t n, size_t m, void *arg)
{
	response_data_t *res_data = (response_data_t *)arg;
	int count = m * n;
	memcpy(res_data, ptr, count);
	return count;
}

// 获取用户sessionid
char *create_sessionid(char *sessionid)
{
	char uuid[36] = {0};
	sprintf(sessionid, "online-user-%s", get_random_uuid(uuid));
	return sessionid;
}

// 获取sessionid
char *get_random_uuid(char *str)
{
    uuid_t uuid;

    uuid_generate(uuid);
    uuid_unparse(uuid, str);

    return str;
}

char *make_reg_login_res_json(int ret, char* recode, char *sessionid, char *reason)
{
    //packet json
    cJSON *root = cJSON_CreateObject();

    if (ret == 0) {
        cJSON_AddStringToObject(root, "result", "ok");
        cJSON_AddStringToObject(root, "sessionid", sessionid);
    }
    else {
        cJSON_AddStringToObject(root, "result", "error");
        cJSON_AddStringToObject(root, "reason", reason);
    }
    cJSON_AddStringToObject(root, "recode", recode);

    char *response_data = cJSON_Print(root);
    cJSON_Delete(root);

    return response_data;
}
