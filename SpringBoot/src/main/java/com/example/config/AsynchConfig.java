package com.example.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

@EnableAsync
@Configuration
public class AsynchConfig {

	@Bean(name = "asynchExecutor")
	public Executor asynchExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(3);
		executor.setThreadNamePrefix("AsyExe--");
		executor.initialize();
		return executor;
	}
	
	@Bean
	public RestTemplate	restTemplate() {
		return new RestTemplate();
	}

}
