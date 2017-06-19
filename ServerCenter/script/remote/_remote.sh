#! /bin/bash
# this file should be put in this dir of local working station: /data/AppDeploy/script/

clear
# ----------- starting
echo '**********************************'
echo " 操作方法: sh xxx.sh [服务器操作指令,如: start|stop] [运行环境,如: test]           -- byTanj 18Apr2017"
echo " start: 远程启动服务"
echo " stop: 远程停止服务"

PROJECT_HOME=`dirname $PWD`
PARENT_PKG_NAME=`dirname $PWD | rev | awk -F \/ '{print $2}' | rev`
PROJ_NAME=`dirname $PWD | rev | awk -F \/ '{print $1}' | rev`

BASE_HOME=/data/AppSource/$PARENT_PKG_NAME/$PROJ_NAME/
DEPLOY_HOME=$BASE_HOME/target
SCRIPT_HOME=$BASE_HOME/script

REMOTE_HOST=120.26.111.153
REMOTE_PORT=22
REMOTE_USER=root
REMOTE_PWD=

echo '**********************************'
echo "* 父级工程名称 =  $PARENT_PKG_NAME"
echo "* 应用工程名称 =  $PROJ_NAME"
echo "* 本地Jar包目录 =  $PROJECT_HOME/target"
echo "* 远程目标目录 =  $DEPLOY_HOME"
echo "* 远程脚本路径 =  $SCRIPT_HOME"

CMD=$1
# check cmd
if [ ! -n "$CMD" ]; then
    echo '**********************************'
    echo "错误: 必须指定操作命令!"
    exit -1
fi

#
function scp_jar(){
    echo '**********************************'
	echo "正在上传数据..."
	cd $PROJECT_HOME/target/
	echo "正在操作：scp -P $REMOTE_PORT *.jar $REMOTE_USER@$REMOTE_HOST:$DEPLOY_HOME"
	scp -P $REMOTE_PORT *.jar $REMOTE_USER@$REMOTE_HOST:$DEPLOY_HOME
}

#
function run_cmd(){
    echo '**********************************'
	echo "执行远程命令：$SCRIPT_HOME/"
	ssh -p $REMOTE_PORT $REMOTE_USER@$REMOTE_HOST "cd $SCRIPT_HOME; ls -lh; sh $CMD.sh"
}

# check CMD and process
case $CMD in
    upload) # update source code
        scp_jar
    ;;
    stop) # stop
        run_cmd
    ;;
    start)
        # start
        run_cmd
    ;;
    *)
        echo '**********************************'
        echo "命令非法! 退出。";
        exit -1;;
esac