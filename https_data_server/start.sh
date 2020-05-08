kill -9 `ps aux | grep "data_server" |grep -v grep | awk '{print $2}'`

#启动redis-server
redis-server ./conf/redis.conf

#启动data服务器
./data_server 8887 &
