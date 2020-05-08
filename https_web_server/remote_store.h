#ifndef  __REMOTE_STORE_H_
#define  __REMOTE_STORE_H_

#include <event2/bufferevent.h>
#include <event2/bufferevent_ssl.h>
#include <event2/event.h>
#include <event2/http.h>
#include <event2/buffer.h>
#include <event2/util.h>
#include <event2/keyvalq_struct.h>


#include <uuid/uuid.h>
#include "make_log.h"

#define URI_DATA_SERVER_PER		"https://120.26.173.34:8887/persistent"
#define URI_DATA_SERVER_CHE		"https://120.26.173.34:8887/cache"

#define ORDER_ID_NONE                   "NONE"

#define RECODE_OK                       "0"
#define RECODE_SESSION_ERROR            "1"
#define RECODE_SERVER_ERROR             "2"
#define RECODE_NO_DRIVER                "3"

#define LOG_MODULE                      "Subway_Remote"
#define LOG_PROC_LOGIN                  "login"
#define LOG_PROC_REG                    "regist"
#define LOG_PROC_SETORDER               "set_order"
#define LOG_PROC_FINISHORDER            "finish_order"
#define LOG_PROC_REMOTE_CURL            "remote_curl"


#define TIME_STR_LEN                    (32)
#define UUID_STR_LEN                    (36)
#define SESSIONID_STR_LEN               (64)
#define ORDERID_STR_LEN                 (64)
#define RESPONSE_DATA_LEN               (4096)
#define USERNAME_STR_LEN                (256)

//id超时时间10分钟
#define SESSIONID_LIFECYCLE             (600)


typedef struct curl_response_data
{
    char data[RESPONSE_DATA_LEN];
    int data_len;

}curl_response_data_t;


//返回前端json数据包封装
char *make_reg_login_res_json(int ret, char* recode, char *sessionid, char *reason);
char * create_sessionid(char *sessionid);


/* -------------------------------------------*/
/**
 * @brief  远程查询登陆信息表
 *
 * @param username
 * @param password
 *
 * @returns   
 *  0 succ  -1 fail
 */
/* -------------------------------------------*/
int curl_to_dataserver_login(const char *username, const char *password);


/* -------------------------------------------*/
/**
 * @brief  远程存储SESSIONID - id_card  临时
 *
 * @param username
 * @param sessionid 
 * @param orderid 
 *
 * @returns   
 *          0 succ, -1 fail
 */
/* -------------------------------------------*/
int curl_to_cacheserver_session(const char *username,  const char* sessionid, const char *orderid);


/* -------------------------------------------*/
/**
 * @brief   远程设置一个key的超时时间(生命周期)
 *
 * @param sessionid
 * @param lifecycle
 *
 * @returns   
 */
/* -------------------------------------------*/
int curl_to_cacheserver_lifecycle(const char *sessionid, int lifecycle);


/* -------------------------------------------*/
/**
 * @brief  远程注册用户请求业务
 *
 * @param username
 * @param password    
 *
 * @returns   
 *              0 succ, -1 fail
 */
/* -------------------------------------------*/
int curl_to_dataserver_reg(const char* username, 
                           const char* password);

#endif
