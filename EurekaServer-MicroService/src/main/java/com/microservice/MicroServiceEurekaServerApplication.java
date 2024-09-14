package com.microservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer
public class MicroServiceEurekaServerApplication {
	private static final Logger logger = LoggerFactory.getLogger(MicroServiceEurekaServerApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(MicroServiceEurekaServerApplication.class, args);
		logger.info("Eureka server Running");
	}
}