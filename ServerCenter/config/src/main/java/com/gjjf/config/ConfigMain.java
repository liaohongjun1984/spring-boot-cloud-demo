package com.gjjf.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.ApplicationContext;

@EnableConfigServer
@SpringBootApplication      //springBoot注解,spring在springBoot基础之上来构建项目
@EnableDiscoveryClient
public class ConfigMain {
    private final static Logger logger = LoggerFactory.getLogger(ConfigMain.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ConfigMain.class, args);
        String[] activeProfiles = ctx.getEnvironment().getActiveProfiles();
        for (String profile : activeProfiles) {
            logger.info("############################################### Spring Boot using profile: {}", profile);
        }
    }
}
