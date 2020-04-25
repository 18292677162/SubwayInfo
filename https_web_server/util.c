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