package com.scoe.doconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EnableDiscoveryClient
@SpringBootApplication
public class DoConnectApplication {
	private static final Logger logger = LoggerFactory.getLogger(DoConnectApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(DoConnectApplication.class, args);
		logger.info("Admin Service Running");	
	}
}



