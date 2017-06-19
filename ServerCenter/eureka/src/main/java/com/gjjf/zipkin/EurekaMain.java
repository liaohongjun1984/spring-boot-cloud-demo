package com.gjjf.zipkin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ApplicationContext;

@EnableEurekaServer         //开启eureka服务
@SpringBootApplication      //springBoot注解,spring在springBoot基础之上来构建项目
public class EurekaMain {
    private final static Logger logger = LoggerFactory.getLogger(EurekaMain.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(EurekaMain.class, args);
        String[] activeProfiles = ctx.getEnvironment().getActiveProfiles();
        for (String profile : activeProfiles) {
            logger.info("############################################### Spring Boot using profile: {}", profile);
        }
    }
}