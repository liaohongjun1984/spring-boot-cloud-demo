
## 工程说明
* 服务注册中心（服务管理所有服务治理中心）

## 工程创建和启动
* 创建Spring Boot的程序主类，并添加@EnableEurekaServer  注解，开启Eureka Server
* 工程很方便启动，运行EurekagMain.java即可
* 启动后 localhost:11001 打开浏览器可以查看eureka管理后台
   
## 工程启动注意事项

* 所有服务启动后注册到eureka，所以先启动eureka，后启动注册服务




