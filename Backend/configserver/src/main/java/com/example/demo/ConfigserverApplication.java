package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Main class for the Config Server application. This class is responsible for
 * bootstrapping the Spring Boot application and enabling the Config Server
 * functionality.
 */
@EnableConfigServer
@SpringBootApplication
public class ConfigserverApplication {

	/**
	 * The main method serves as the entry point for the Spring Boot application.
	 *
	 * @param args command-line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(ConfigserverApplication.class, args);
	}
}