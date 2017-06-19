
## 工程说明
* 用户中心微服务工程 
   
## 工程依赖关系

* 依赖 common-utils （基础工具jar包）
* 依赖 common-db （基础数据库配置jar包）
* 依赖 common-redis（基础redis配置jar包）
* 依赖 user-protocol （用户微服务的协议和对外暴露接口的jar包）

## 工程启动

* 服务启动后注册到eureka，所以先启动eureka，后启动注册服务
* mvn install common-utils
* mvn install common-db 
* mvn install common-redis
* mvn install user-protocol
* 工程启动，运行UserMSMain.java即可

## 工程启动后测试
* 服务是否注册到eureka中心查看启动的服务 localhost:11001
* 测试服务接口是否正常



