package com.example.config;

import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.example.listener.BatchJobListener;

public class ParallelProcessingConfig implements Partitioner {

	@Autowired
	JobBuilderFactory jobBuilderFactory;
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	@Autowired
	BatchJobListener listener;

	@Bean
	public Job job() {
	    return jobBuilderFactory.get("job")
	        .start(splitFlow())
	        .next(step4())
	        .build()        //builds FlowJobBuilder instance
	        .build();       //builds Job instance
	}

	private Step step4() {
		// TODO Auto-generated method stub
		return null;
	}

	@Bean
	public Flow splitFlow() {
	    return new FlowBuilder<SimpleFlow>("splitFlow")
	        .split(taskExecutor())
	        .add(flow1(), flow2())
	        .build();
	}

	@Bean
	public Flow flow1() {
	    return new FlowBuilder<SimpleFlow>("flow1")
	        .start(step1())
	        .next(step2())
	        .build();
	}

	private Step step2() {
		// TODO Auto-generated method stub
		return null;
	}

	@Bean
	public Flow flow2() {
	    return new FlowBuilder<SimpleFlow>("flow2")
	        .start(step3())
	        .build();
	}

	private Step step3() {
		// TODO Auto-generated method stub
		return null;
	}

	@Bean
	public Step step1Manager() {
	    return stepBuilderFactory.get("step1.manager")
	        .partitioner("step1", partitioner())
	        .partitionHandler(partitionHandler())
	        .build();
	}

	private Partitioner partitioner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Bean
	public PartitionHandler partitionHandler() {
	    TaskExecutorPartitionHandler retVal = new TaskExecutorPartitionHandler();
	    retVal.setTaskExecutor(taskExecutor());
	    retVal.setStep(step1());
	    retVal.setGridSize(10);
	    return retVal;
	}

	private Step step1() {
		// TODO Auto-generated method stub
		return null;
	}

	@Bean
	public TaskExecutor taskExecutor() {
	    return new SimpleAsyncTaskExecutor("spring_batch");
	}

	@Bean
	public Step sampleStep(TaskExecutor taskExecutor) {
		return this.stepBuilderFactory.get("sampleStep")
					.<String, String>chunk(10)
					.reader(itemReader())
					.writer(itemWriter())
					.taskExecutor(taskExecutor)
					.build();
	}

	private ItemWriter<? super String> itemWriter() {
		// TODO Auto-generated method stub
		return null;
	}

	private ItemReader<? extends String> itemReader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Bean
	public MultiResourceItemReader itemReader(
		@Value("#{stepExecutionContext['fileName']}/*") Resource [] resources) {
		return new MultiResourceItemReaderBuilder<String>()
				.delegate(fileReader())
				.name("itemReader")
				.resources(resources)
				.build();
	}
	
	private ResourceAwareItemReaderItemStream<? extends String> fileReader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		return null;
	}
	
}
