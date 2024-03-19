package com.example;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringKafkaRegistryApplication {

	final static Logger logger = Logger.getLogger(SpringKafkaRegistryApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaRegistryApplication.class, args);
			
	}
	
}
