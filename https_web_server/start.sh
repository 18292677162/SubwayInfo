kill -9 `ps aux | grep "web_server" |grep -v grep | awk '{print $2}'`

#启动web服务器
./web_server 8888 &
