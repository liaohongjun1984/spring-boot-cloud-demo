package com.idohoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication(scanBasePackages = {"com.idohoo"})
@EnableDiscoveryClient
@EnableAutoConfiguration
public class UserMSMain {

	public static void main(String[] args) {
		
		SpringApplication.run(UserMSMain.class, args);
		
	}
	
}
