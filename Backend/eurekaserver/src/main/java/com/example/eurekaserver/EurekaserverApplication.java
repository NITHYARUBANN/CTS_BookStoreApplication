package com.example.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Main class for the Eureka Server application. This class is responsible for
 * bootstrapping the Spring Boot application and enabling the Eureka Server
 * functionality.
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaserverApplication {

	/**
	 * The main method serves as the entry point for the Spring Boot application.
	 *
	 * @param args command-line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(EurekaserverApplication.class, args);
	}
}