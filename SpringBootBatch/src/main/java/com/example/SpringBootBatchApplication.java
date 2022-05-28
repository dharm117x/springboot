package com.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableBatchProcessing
@SpringBootApplication
@EnableScheduling
public class SpringBootBatchApplication {
	
	@Autowired
	JobLauncher jobLauncher;
	@Autowired
	@Qualifier("multiStepJob")
	Job multiStepJob;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootBatchApplication.class, args);
	}

	@Scheduled(cron = "0 */1 * * * ?")
	public void perform() throws Exception {
		JobParameters params = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		jobLauncher.run(multiStepJob, params);
	}
}
