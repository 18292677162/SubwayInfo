#ifndef _DAO_MYSQL_H_
#define _DAO_MYSQL_H_

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include <mysql.h>

#define MYSQL_USER          "root"
#define MYSQL_PWD           "1"
#define MYSQL_DATABASE      "Subway"
#define SQL_MAX_LEN         (1024)

void print_error(MYSQL *conn, const char *title);

void process_result_set(MYSQL *conn,MYSQL_RES *res_set);

MYSQL* msql_conn(char *user_name, char* passwd, char *db_name);

#endif