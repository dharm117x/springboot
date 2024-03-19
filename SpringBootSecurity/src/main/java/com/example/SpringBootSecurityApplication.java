package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootSecurityApplication {
	
	//Exclude embeded tomcat server
	//ApplicationContextException:Unable to start ServletWebServerApplicationContext due to missing ServletWebServerFactory bea
	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityApplication.class, args);
	}

}
