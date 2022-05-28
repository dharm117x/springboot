package com.example.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.listener.BatchJobListener;
import com.example.step.Processor;
import com.example.step.Reader;
import com.example.step.Writer;

@Configuration
public class BatchConfig {

	@Autowired
	JobBuilderFactory jobBuilderFactory;
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	@Autowired
	BatchJobListener listener;
	
	@Bean
	public Job processJob() {
		System.out.println("BatchConfig.processJob()");
		return jobBuilderFactory.get("processJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(orderStep1()).end()
				.build();
	}

	@Bean
	public Step orderStep1() {
		System.out.println("BatchConfig.orderStep1()");
		return stepBuilderFactory.get("orderStep1").<String, String>chunk(2)
				.reader(new Reader())
				.processor(new Processor())
				.writer(new Writer())
				.build();
	}

}
