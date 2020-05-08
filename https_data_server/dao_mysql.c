#include "dao_mysql.h"



void print_error(MYSQL *conn, const char *title)
{
    fprintf(stderr,"%s:\nError %u (%s)\n",title,mysql_errno(conn),mysql_error(conn));
}

void process_result_set(MYSQL *conn,MYSQL_RES *res_set)
{
    MYSQL_ROW row;
    uint i;

    while ((row = mysql_fetch_row(res_set)) != NULL){

        for(i=0;i<mysql_num_fields(res_set);i++){
            if (i > 0)
                fputc('\t',stdout);
            printf("%s",row[i] != NULL ? row[i] : "NULL");
        }

        fputc('\n',stdout);
    }

    if(mysql_errno(conn) != 0) {
        print_error(conn,"mysql_fetch_row() failed");
    }
    else {
        printf("%lu rows returned \n",
                (ulong)mysql_num_rows(res_set));
    }
}

MYSQL* msql_conn(char *user_name, char* passwd, char *db_name)
{
    MYSQL *conn = NULL;
    
    char *opt_host_name = "127.0.0.1";          /*服务器主机名称 默认为localhost*/
    uint opt_port_num = 0;               /*端口 使用内建值*/
    char *opt_socket_name = NULL;        /*socket name (use build-in value)*/
    uint opt_flags = 0;                  /*连接参数*/

    conn = mysql_init(NULL);
    if (conn == NULL) {
        fprintf(stderr, "mysql 初始化失败\n");
        return NULL;
    }

    if ( mysql_real_connect(conn, opt_host_name, "root", "1", "Subway", opt_port_num, opt_socket_name, opt_flags) == NULL)
    {
        fprintf(stderr, "mysql_conn 失败:Error %u(%s)\n",
                mysql_errno(conn), mysql_error(conn));
        mysql_close(conn);
        return NULL;
    }

    return conn;
}

#if 0

#endif


