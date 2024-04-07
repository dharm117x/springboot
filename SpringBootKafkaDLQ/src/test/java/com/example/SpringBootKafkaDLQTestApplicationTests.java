package com.example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.model.Payment;
import com.example.resource.ConsumerService;
import com.example.resource.ProducerService;

@SpringBootTest
public class SpringBootKafkaDLQTestApplicationTests {

	@Test
	void contextLoads() {
	}

	@Mock
	ConsumerService consumer;
	
	@Mock
	ProducerService producer;

	@Test
	public void consumesuccess_thennotdlt_message()  throws Exception{
		CountDownLatch mainTopicCDL = new CountDownLatch(1);
		doAnswer(invoaction -> {
			mainTopicCDL.countDown(); 
			return null;
		}).when(consumer).handlePayment(any(), any());
		
		producer.send("payments", "dlt-fail-main");
		
		assertThat(mainTopicCDL.await(5, TimeUnit.SECONDS)).isTrue();
		verify(consumer,never()).handlePayment(any(), any());
	}
	
	@Test
	public void consumefails_thendltprocessingStops()  throws Exception{
		CountDownLatch mainTopicCDL = new CountDownLatch(1);
		CountDownLatch dltTopicCDL = new CountDownLatch(2);
		
		doAnswer(invoaction -> {
			mainTopicCDL.countDown(); 
			throw new Exception("Simulation Error main topic");
		}).when(consumer).handlePayment(any(), any());

		doAnswer(invoaction -> {
			dltTopicCDL.countDown(); 
			throw new Exception("Simulation Error DLT topic");
		}).when(consumer).handlePayment(any(), any());
		
		producer.send("payments", "dlt-fail");
		
		assertThat(mainTopicCDL.await(5, TimeUnit.SECONDS)).isTrue();
		assertThat(dltTopicCDL.await(5, TimeUnit.SECONDS)).isFalse();
		assertThat(dltTopicCDL.getCount()).isEqualTo(1);
		
	}

	
}
