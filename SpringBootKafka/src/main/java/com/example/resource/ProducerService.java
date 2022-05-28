package com.example.resource;

import javax.annotation.Resource;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.UserDo;

@RestController
@RequestMapping("/kafka")
public class ProducerService {

	@Resource
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Resource
	private KafkaTemplate<String, UserDo> userKafkaTemplate;
	
	String KAFKA_TOPIC = "TestTopic";
	@GetMapping("/publish/{message}")
	public String stringMessage(@PathVariable String message) {
		System.out.println("ProducerService.stringMessage()");
		kafkaTemplate.send(KAFKA_TOPIC, message);
		
		return "String Message post sucessed";
	}

	@GetMapping("/publish/user/{user}")
	public String userMessage(@PathVariable String user) {
		System.out.println("ProducerService.userMessage()");
		userKafkaTemplate.send(KAFKA_TOPIC, new UserDo(user, "asd@gmail.com", 11111111L));
		
		return "User Message post sucessed";
	}

}
