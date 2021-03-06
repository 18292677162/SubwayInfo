#include "util.h"
#include "remote_store.h"

/* This callback gets invoked when we get any http request that doesn't match
 * any other callback.  Like any evhttp server callback, it has a simple job:
 * it must eventually call evhttp_send_error() or evhttp_send_reply().
 */
void login_cb(struct evhttp_request *req, void *arg)
{ 
    int ret = 0;
    struct evbuffer *evb = NULL;
    const char *uri = evhttp_request_get_uri (req);
    struct evhttp_uri *decoded = NULL;

    /* 判断 req 是否是GET 请求 */
    if (evhttp_request_get_command (req) == EVHTTP_REQ_GET)
    {
        struct evbuffer *buf = evbuffer_new();
        if (buf == NULL) return;
        evbuffer_add_printf(buf, "Requested: %s\n", uri);
        evhttp_send_reply(req, HTTP_OK, "OK", buf);
        return;
    }

    /* 只处理Post请求, Get请求，就直接return 200 OK  */
    if (evhttp_request_get_command (req) != EVHTTP_REQ_POST)
    { 
        evhttp_send_reply (req, 200, "OK", NULL);
        return;
    }

    printf ("Got a POST request for <%s>\n", uri);

    //判断此URI是否合法
    decoded = evhttp_uri_parse (uri);
    if (! decoded)
    { 
        printf ("It's not a good URI. Sending BADREQUEST\n");
        evhttp_send_error (req, HTTP_BADREQUEST, 0);
        return;
    }

    /* Decode the payload */
    struct evbuffer *buf = evhttp_request_get_input_buffer (req);
    evbuffer_add (buf, "", 1);    /* NUL-terminate the buffer */
    char *payload = (char *) evbuffer_pullup (buf, -1);
    int post_data_len = evbuffer_get_length(buf);
    char request_data_buf[4096] = {0};
    memcpy(request_data_buf, payload, post_data_len);

    printf("[post_data][%d]=\n %s\n", post_data_len, payload);


    /*
       具体的：可以根据Post的参数执行相应操作，然后将结果输出
       ...
    */
    //unpack json
    cJSON* root = cJSON_Parse(request_data_buf);
    cJSON* username = cJSON_GetObjectItem(root, "username");
    cJSON* password = cJSON_GetObjectItem(root, "password");
    cJSON* regist = cJSON_GetObjectItem(root, "regist");

    printf("username = %s\n", username->valuestring);
    printf("password = %s\n", password->valuestring);
    printf("regist = %s\n", regist->valuestring);


    // 查询数据库
    ret = curl_to_dataserver_login(username->valuestring, password->valuestring);

    char *recode = RECODE_OK;

    char sessionid[SESSIONID_STR_LEN] = {0};
    if (ret == 0) {
        //生成sessionid
        create_sessionid(sessionid);
        ret = curl_to_cacheserver_session(username->valuestring, sessionid, ORDER_ID_NONE);
    }

    if (ret == 0) {
        //设置key的超时时间
        ret = curl_to_cacheserver_lifecycle(sessionid, SESSIONID_LIFECYCLE);
    }


    //将sessionid存放到缓存数据库中
    char *response_data = make_reg_login_res_json(ret, recode, sessionid, "login error");


    cJSON_Delete(root);

/*
    //packet json
    root = cJSON_CreateObject();

    cJSON_AddStringToObject(root, "result", "ok");
    //cJSON_AddStringToObject(root, "sessionid", "xxxxxxxx");

    char *response_data = cJSON_Print(root);
    cJSON_Delete(root);
*/


    /* This holds the content we're sending. */

    //HTTP header
    evhttp_add_header(evhttp_request_get_output_headers(req), "Server", MYHTTPD_SIGNATURE);
    evhttp_add_header(evhttp_request_get_output_headers(req), "Content-Type", "text/plain; charset=UTF-8");
    evhttp_add_header(evhttp_request_get_output_headers(req), "Connection", "close");

    evb = evbuffer_new ();
    evbuffer_add_printf(evb, "%s", response_data);
    //将封装好的evbuffer 发送给客户端
    evhttp_send_reply(req, HTTP_OK, "OK", evb);

    if (decoded)
        evhttp_uri_free (decoded);
    if (evb)
        evbuffer_free (evb);


    printf("[response]:\n");
    printf("%s\n", response_data);

    free(response_data);
}