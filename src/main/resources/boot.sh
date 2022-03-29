#!/bin/bash
#这里可替换为你自己的执行程序，其他代码无需更改
APP1_NAME=rule-app-1.0-SNAPSHOT.jar
APP2_NAME=jar2包路径
DEFAULT_ACTIVE_CONFIG="dev"
ENV_CONFIG=$2

#使用说明，用来提示输入参数
usage() {
    echo "Usage: sh demo.sh [start|stop|restart|status] [dev|test|prod]"
    exit 1
}

#检查程序是否在运行
is_exist_admin() { 
    pid=`ps -ef | grep $APP1_NAME | grep -v grep | awk '{print $2}' `
    #如果不存在返回1，存在返回0
    if [ -z "${pid}" ]; then
      return 1
    else
      return 0
    fi
}
is_exist_web() { 
    pid=`ps -ef | grep $APP2_NAME | grep -v grep | awk '{print $2}' `
    #如果不存在返回1，存在返回0
    if [ -z "${pid}" ]; then
      return 1
    else
      return 0
    fi
}

#启动方法
start() {
   is_exist_admin
   if [ $? -eq "0" ]; then
    # java -jar ${APP1_NAME}
     echo "${APP1_NAME} is already running. pid=${pid} ."
   else
     if [[ -z "$ENV_CONFIG"  ]] ; then
          ACTIVE_CONFIG="--spring.profiles.active="${DEFAULT_ACTIVE_CONFIG}
     else
        ACTIVE_CONFIG="--spring.profiles.active="$ENV_CONFIG
     fi
     nohup java -jar -Xms256m -Xmx1024m  $APP1_NAME ${ACTIVE_CONFIG} > /dev/null 2>&1 &
     echo ">>> start $APP1_NAME succeeded PID=$! <<<"
   fi
}

#停止方法
stop() {
   is_exist_admin
   if [ $? -eq "0" ]; then
     kill -9 $pid
     echo ">>> $APP1_NAME process stopped <<<"
     echo "${APP1_NAME} is not running"
   fi
}

#输出运行状态
status() {
   is_exist_admin
   if [ $? -eq "0" ]; then
     echo "${APP1_NAME} is running. Pid is ${pid}"
   else
     echo "${APP1_NAME} is not running."
   fi

}

#重启
restart() {
   stop
   start
}

#根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
   "start")
     start
     ;;
   "stop")
     stop
     ;;
   "status")
     status
     ;;
   "restart")
     restart
     ;;
   *)
     usage
     ;;
esac
