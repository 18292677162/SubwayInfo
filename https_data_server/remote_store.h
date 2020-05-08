#ifndef  __REMOTE_STORE_H_
#define  __REMOTE_STORE_H_


void persistent_store_cb (struct evhttp_request *req, void *arg);
void cache_store_cb (struct evhttp_request *req, void *arg);



char* deal_persistent(char *request_data_buf);
char* deal_cache(char *request_data_buf);

#endif
