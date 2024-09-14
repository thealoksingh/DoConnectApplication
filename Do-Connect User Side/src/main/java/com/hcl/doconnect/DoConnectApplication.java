package com.hcl.doconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@EnableDiscoveryClient
public class DoConnectApplication {

	private static final Logger logger = LoggerFactory.getLogger(DoConnectApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(DoConnectApplication.class, args);
		logger.info("User Service Running");
		
	}

}
