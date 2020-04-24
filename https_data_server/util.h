#ifndef _UTIL_H_
#define _UTIL_H_

#define MYHTTPD_SIGNATURE   "MoCarHttpd v0.1"

#include "https-common.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <limits.h>

#include <sys/types.h>
#include <sys/stat.h>

#include <sys/stat.h>
#include <sys/socket.h>
#include <fcntl.h>
#include <unistd.h>
#include <dirent.h>

#include <openssl/ssl.h>
#include <openssl/err.h>

#include <event2/bufferevent.h>
#include <event2/bufferevent_ssl.h>
#include <event2/event.h>
#include <event2/http.h>
#include <event2/buffer.h>
#include <event2/util.h>
#include <event2/keyvalq_struct.h>

#include <cJSON.h>

#include <curl/curl.h>
#include <cJSON.h>

void persistent_cb(struct evhttp_request *req, void *arg);
void cache_cb(struct evhttp_request *req, void *arg);

#endif