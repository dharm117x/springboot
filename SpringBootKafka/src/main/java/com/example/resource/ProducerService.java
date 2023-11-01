package com.example.resource;

import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.UserDo;

@RestController
@RequestMapping("/kafka")
public class ProducerService {

	private static String KAFKA_TOPIC = "TestTopic";

	@Resource
	private KafkaTemplate<String, String> kafkaTemplate;

	@Resource
	private KafkaTemplate<String, UserDo> userKafkaTemplate;

	@GetMapping("/test/{message}")
	public String testTopic(@PathVariable String message) throws InterruptedException, ExecutionException {

		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(KAFKA_TOPIC, message);
		SendResult<String, String> result = future.get();
		ProducerRecord<String, String> record = result.getProducerRecord();
		System.out.println(record);

		return "String Message post sucessed";
	}

	@GetMapping("/publish/{message}")
	public String stringMessage(@PathVariable String message) {
		System.out.println("ProducerService.stringMessage()");
		kafkaTemplate.send(KAFKA_TOPIC, "testkey", message);

		return "String Message post sucessed";
	}

	@GetMapping("/publish/user/{user}")
	public String userMessage(@PathVariable String user) {
		System.out.println("ProducerService.userMessage()");
		userKafkaTemplate.send(KAFKA_TOPIC, new UserDo(user, "asd@gmail.com", 11111111L));

		return "User Message post sucessed";
	}

}
