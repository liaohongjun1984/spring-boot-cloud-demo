package com.idohoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = {"com.idohoo"})
@EnableDiscoveryClient
@EnableFeignClients
//开启spring对Aspectj的支持
@EnableAspectJAutoProxy
//异步线程任务
@EnableAsync
public class FrontApiMain {

	public static void main(String[] args) {

		SpringApplication.run(FrontApiMain.class, args);

	}

}