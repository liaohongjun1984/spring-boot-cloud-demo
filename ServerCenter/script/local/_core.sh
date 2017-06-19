#! /bin/bash
# this file should be put in this dir of BOTH local working station AND remote server: /data/AppDeploy/script/
# all operations depend on this shell .

clear

# ----------- starting
echo '**********************************'
echo " 操作方法: sh xxx.sh [服务器操作指令,如: update|install|package|deploy|start|stop] [运行环境,如: test]           -- byTanj 18Apr2017"
echo " update: 从版本库更新最新代码"
echo " install: 编译并安装到本地依赖类库"
echo " package: 编译并打包"
echo " deploy: 将jar文件复制到部署目录"
echo " start: 启动java服务"
echo " stop: 停止java服务"


PROJECT_HOME=`dirname $PWD`
PARENT_PKG_NAME=`dirname $PWD | rev | awk -F \/ '{print $2}' | rev`
PROJ_NAME=`dirname $PWD | rev | awk -F \/ '{print $1}' | rev`

DEPLOY_HOME=/data/AppDeploy/$PARENT_PKG_NAME/$PROJ_NAME
DEPLOY_PID_FILE=$DEPLOY_HOME/.pid
LOGGER_HOME=/data/logs/$PARENT_PKG_NAME

echo '**********************************'
echo "* 本地工作副本目录 =  $PROJECT_HOME"
echo "* 父级工程名称 =  $PARENT_PKG_NAME"
echo "* 应用工程名称 =  $PROJ_NAME"
echo "* 应用部署目录 =  $DEPLOY_HOME"
echo "* pid文件 =  $DEPLOY_PID_FILE"
echo "* 日志文件 =  $LOGGER_HOME/$PROJ_NAME.log"

CMD=$1

shift
EXTRA_ARGS=$*

#
function vcs_update(){ # update source code from vcs repository
	cd $PROJECT_HOME
	echo '**********************************'
	echo "正在从版本库更新代码..."
	git pull
	echo '**********************************'
	echo "代码更新完毕"
}
# install project
function build_and_install(){
	echo '**********************************'
	echo "开始编译并install..."
	cd $PROJECT_HOME
	mvn clean install
	echo '**********************************'
	echo "编译并安装到本地库完成，结果："
	echo "ls -lh `find ~/.m2/repository/ -type d | grep $PROJ_NAME`"
	ls -lh `find ~/.m2/repository/ -type d | grep $PROJ_NAME`
}
# clean package
function build_and_package(){ # compile project
	echo '**********************************'
	echo "开始编译并打包..."
	cd $PROJECT_HOME
	mvn clean package
	echo '**********************************'
	echo "编译并打包完成，结果："
	echo "ls -lh $PROJECT_HOME/target/"
	ls -lh $PROJECT_HOME/target/
}
# check if the deploy config is correct or not
function chk_deploy_dir(){
    if [ ! -e "$DEPLOY_HOME" ]; then
	    echo '**********************************'
	    echo "项目 '$PROJ_NAME' 部署目录不存在, 现在创建: $DEPLOY_HOME"
	    mkdir -p $DEPLOY_HOME
	fi
}
#
function cp_new_jar(){
    echo '**********************************'
    echo "备份部署目录下的文件: $DEPLOY_HOME/*.jar"
    cp $DEPLOY_HOME/$PROJ_NAME.jar $DEPLOY_HOME/$PROJ_NAME.jar_bkp_`date "+%Y%m%d%H%M%S"`
    echo '**********************************'
    echo "删除部署目录下的文件: $DEPLOY_HOME/*.jar"
    ls -lh $DEPLOY_HOME/*.jar
    rm -f $DEPLOY_HOME/*.jar
    echo '**********************************'
    echo "正在复制新启动文件并记录新版本信息: cp $PROJECT_HOME/target/*.jar $DEPLOY_HOME/$PROJ_NAME.jar"
    if [ ! -e "$DEPLOY_HOME" ]; then
        mkdir -p $DEPLOY_HOME
    fi
    ls -lh $PROJECT_HOME/target/*.jar  >> $DEPLOY_HOME/version.log
    echo 'current version: '`date "+%Y%m%d%H%M%S"`>> $DEPLOY_HOME/version.log
    cp $PROJECT_HOME/target/*.jar $DEPLOY_HOME/$PROJ_NAME.jar
    echo '**********************************'
    echo "复制完毕：ls -lh $DEPLOY_HOME"
    ls -lh $DEPLOY_HOME/
}
# stop server
function stop_app(){
    echo '**********************************'
    echo "正在停止服务: $PROJ_NAME"
    if [ ! -e "$DEPLOY_PID_FILE" ]; then
        echo '**********************************'
        echo "找不到进程id文件：$DEPLOY_PID_FILE"
    else
        cat $DEPLOY_PID_FILE | xargs kill -15
        rm -f $DEPLOY_PID_FILE
        echo '**********************************'
        echo "服务已停止: $PROJ_NAME"
    fi
}
# start server
function start_app(){
    # backup logger file
    if [ ! -e "$LOGGER_HOME" ]; then
        mkdir -p $LOGGER_HOME
    fi
    mv $LOGGER_HOME/$PROJ_NAME.log $LOGGER_HOME/$PROJ_NAME.log_bkp_`date "+%Y%m%d%H%M%S"`
    echo '**********************************'
    echo "启动服务: java -jar $EXTRA_ARGS $DEPLOY_HOME/$PROJ_NAME.jar >> $LOGGER_HOME/$PROJ_NAME.log 2>&1 &"
    java -jar $EXTRA_ARGS $DEPLOY_HOME/$PROJ_NAME.jar >> $LOGGER_HOME/$PROJ_NAME.log 2>&1 &
    echo $! > $DEPLOY_PID_FILE
}
# show log
function show_log(){
    echo '**********************************'
    echo "查看启动日志文件：tail -f $LOGGER_HOME/$PROJ_NAME.log"
    tail -f $LOGGER_HOME/$PROJ_NAME.log
}

# check cmd
if [ ! -n "$CMD" ]; then
    echo '**********************************'
    echo "错误: 必须指定操作命令!"
    exit -1
fi

# check CMD and process
case $CMD in
    update) # update source code
        vcs_update
    ;;
    install) # install into local maven repo
        build_and_install
    ;;
    package) # clean and compile
        build_and_package
    ;;
    stop) # stop
        stop_app
    ;;
    deploy)
        # stop
        stop_app
        # clear all packages, and copy new packages from build to deploy
        chk_deploy_dir
        cp_new_jar
    ;;
    start)
        # stop
        stop_app
        # restart
        start_app
        # show log
        show_log
    ;;
    restart)
        # stop
        stop_app
        # start
        start_app
        # show log
        show_log
    ;;
    *)
        echo '**********************************'
        echo "命令非法! 退出。";
        exit -1;;
esac