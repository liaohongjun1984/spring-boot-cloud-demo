
## 工程说明
* 服务配置中心（服务管理配置信息）

## 工程创建和启动
* 创建Spring Boot的程序主类，并添加@EnableConfigServer注解，开启Config Server
* 启动前先 clean install -Dmaven.test.skip=true -Pdev 
* 配置文件的值，是使用pom.xml中profile来区分不同环境的值（开发环境、测试环境、生产环境）
* 工程很方便启动，运行ConfigMain.java即可
* 启动后可以在 localhost:11001 注册的配置中心服务
* 配置中心属性的值的查看，通过 例如 ：localhost:11002/jdbc-dev.properties 查看数据库配置信息

## 配置文件管理
* 配置文件管理支持 git 、svn 、本地管理等；
* Spring Cloud Config也提供本地存储配置的方式。
   我们只需要设置属性spring.profiles.active=native，
   Config Server会默认从应用的src/main/resource目录下检索配置文件
   
## 工程启动注意事项

* 配置中心服务启动后注册到eureka，所以先启动eureka，后启动配置中心




