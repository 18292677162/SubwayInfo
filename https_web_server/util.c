#include "util.h"

// curl 处理存储服务器返回数据的回调
size_t deal_response(void *ptr, size_t n, size_t m, void *arg)
{
	response_data_t *res_data = (response_data_t *)arg;
	int count = m * n;
	memcpy(res_data, ptr, count);
	return count;
}