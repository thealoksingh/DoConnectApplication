package com.hcl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;


@SpringBootApplication
@EnableConfigServer  //application is acting as a server
public class DoConnectSpringCloudServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoConnectSpringCloudServerApplication.class, args);
		System.out.println("Cloud Running");
	}

}
