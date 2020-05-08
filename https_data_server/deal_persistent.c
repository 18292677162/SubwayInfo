#include "dao_mysql.h"
#include "tables.h"
#include "util.h"
#include "cJSON.h"

static int process_result(MYSQL *conn, MYSQL_RES *res_set, char *pwd)
{
    MYSQL_ROW row;
    uint i;
    ulong line = 0;


    if (mysql_errno(conn) != 0) {
        LOG(LOG_MODULE_SERVER_DATA, LOG_PROC_PERSISTENT, "mysql_fetch_row() failed");
        return -1;
    }

    line = mysql_num_rows(res_set);
    LOG(LOG_MODULE_SERVER_DATA, LOG_PROC_PERSISTENT, "%lu rows returned \n", line);
    if (line == 0) {
        return -1;
    }
    

    while ((row = mysql_fetch_row(res_set)) != NULL) {
        for (i = 0; i<mysql_num_fields(res_set); i++)  {
            if (row[i] != NULL) {
                LOG(LOG_MODULE_SERVER_DATA, LOG_PROC_PERSISTENT, "%d row is %s", i, row[i]);
                if (strcmp(row[i], pwd) == 0) {
                    return 0;
                }
            }
        }
    }

    return -1;
}

/* -------------------------------------------*/
/**
 * @brief  做登陆判断
 *
 * @param username
 * @param pwd
 * @param driver
 *
 * @returns   
 *  0 succ -1 fail
 */
/* -------------------------------------------*/
static int check_username(char *username, char *pwd)
{
    char sql_cmd[SQL_MAX_LEN] = {0};
    int retn = 0;

    MYSQL *conn = msql_conn(g_db_config.db_username, g_db_config.db_passwd, g_db_config.db_basename);
    if (conn == NULL) {
        return -1;
    }

    sprintf(sql_cmd, "select password from %s where u_name=\"%s\"", TABLE_USER, username);

    if (mysql_query(conn, sql_cmd)) {
        LOG(LOG_MODULE_SERVER_DATA, LOG_PROC_PERSISTENT,"[-]%s error!", sql_cmd);
        retn = -1;
        goto END;
    }
    else {
        MYSQL_RES *res_set;
        res_set = mysql_store_result(conn);/*生成结果集*/
        if (res_set == NULL) {
            LOG(LOG_MODULE_SERVER_DATA, LOG_PROC_PERSISTENT, "mysql_store_result error!", sql_cmd);
            retn = -1;
            goto END;
        }

        retn = process_result(conn, res_set, pwd);
    }

END:
    mysql_close(conn);

    return retn;
}

/* -------------------------------------------*/
/**
 * @brief  插入用户信息表
 *
 * @param username
 * @param password
 * @param tel
 * @param email
 * @param id_card
 * @param driver
 *
 * @returns   
 */
/* -------------------------------------------*/
static int insert_table_user(char *username, char *password)
{
    int ret = 0;
    char query[SQL_MAX_LEN] = {0};

    MYSQL *conn = msql_conn(g_db_config.db_username,
                            g_db_config.db_passwd,
                            g_db_config.db_basename);
    if (conn == NULL) {
        printf("====== conn mysql error!=====\n");
        LOG(LOG_MODULE_SERVER_DATA, LOG_PROC_PERSISTENT, "====== conn mysql error!=====\n");
        return -1;
    }

    sprintf(query, 
            "insert into %s (u_name, password, phone) values ('%s', '%s', %s)", 
            TABLE_USER , username, password,username);

    if (mysql_query(conn, query)) {
        printf("query = %s\n", query);
        printf("==== insert %s error===\n", TABLE_USER);
        LOG(LOG_MODULE_SERVER_DATA, LOG_PROC_PERSISTENT, "query = %s\n", query);
        LOG(LOG_MODULE_SERVER_DATA, LOG_PROC_PERSISTENT, "==== insert %s error===\n", TABLE_USER);
        print_error(conn, "insert");
        ret = -1;
    }

    mysql_close(conn);

    return ret;
}




/**
 * @brief  处理持久型 数据库主入口
 *
 * @param request_data_buf
 *
 * @returns   
 */
/* -------------------------------------------*/
char* deal_persistent(char *request_data_buf)
{
    char *response_data;
    int ret = 0;
    //unpack json
    cJSON* root     = cJSON_Parse(request_data_buf);
    cJSON* cmd      = cJSON_GetObjectItem(root, "cmd");
    cJSON* table    = cJSON_GetObjectItem(root, "table");
    //cJSON* busi     = cJSON_GetObjectItem(root, "busi");

    if (strcmp(cmd->valuestring, TABLE_CMD_INSERT) == 0) {

        //入库msql数据库
        if (strcmp(table->valuestring, TABLE_USER) == 0) {

            printf("insert into %s\n", TABLE_USER);
            LOG(LOG_MODULE_SERVER_DATA, LOG_PROC_PERSISTENT, "insert into %s\n", TABLE_USER);

            cJSON* username = cJSON_GetObjectItem(root, "username");
            cJSON* password = cJSON_GetObjectItem(root, "password");

#if 0

#endif

            ret = insert_table_user(username->valuestring,
                                 password->valuestring);

            response_data = make_response_json(ret, "store SUBWAY_TABLE_USER error");
        }
    }

    else if (strcmp(cmd->valuestring, TABLE_CMD_QUERY) == 0) {

            printf("query from %s\n", TABLE_USER);
            LOG(LOG_MODULE_SERVER_DATA, LOG_PROC_PERSISTENT, "query from %s\n", TABLE_USER);

            cJSON* username = cJSON_GetObjectItem(root, "username");
            cJSON* password = cJSON_GetObjectItem(root, "password");

            ret = check_username(username->valuestring, password->valuestring);

            response_data = make_response_json(ret, "query SUBWAY_TABLE_USER error");
    }



    cJSON_Delete(root);

    return response_data;

}


