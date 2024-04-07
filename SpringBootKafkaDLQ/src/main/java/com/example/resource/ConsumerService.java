package com.example.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

import com.example.model.Payment;

@EnableKafka
@Configuration
public class ConsumerService {
	private static final Logger log = LoggerFactory.getLogger(ConsumerService.class);

	@RetryableTopic(attempts = "2", kafkaTemplate = "paymentKafkaTemplate", dltStrategy = DltStrategy.FAIL_ON_ERROR)
	@KafkaListener(topics = {"payments"}, groupId = "payments", containerFactory = "paymnetFactory")
	//@KafkaListener(topics = {"payments-dlt"}, groupId = "payments")
	public void handlePayment(Payment payload,  @Header(name = KafkaHeaders.RECEIVED_TOPIC) String topic ) {
		log.info("Log message on topic {} of payload {} ", topic, payload);
		if("000".equals(payload.getRef())){
			throw new RuntimeException("consumer error for wrong payment ref - 000");
		}
	}
	
	@KafkaListener(topics = {"payments-dlt"}, groupId = "payments", containerFactory = "paymnetFactory")
	public void handlePayment1(Payment payload,  @Header(name = KafkaHeaders.RECEIVED_TOPIC) String topic ) {
		log.info("1 - Log message on topic {} of payload {} ", topic, payload);
	}
	
	@DltHandler
	public void handleDltPayment(Payment payload,  @Header(name = KafkaHeaders.RECEIVED_TOPIC) String topic ) {
		log.info("Log message on topic {} of payload {} ", topic, payload);
		
	}
	
}
