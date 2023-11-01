package com.example.resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

import com.example.model.UserDo;

@EnableKafka
@Configuration
public class ConsumerService {

	@KafkaListener(groupId = "group-string", topics = {"TestTopic"}, containerFactory = "factory" )
	public void getData(String meassage) {
		System.out.println("String Message:" + meassage);
	}
	
	//@KafkaListener(groupId = "group-json", topics = {"TestTopic"}, containerFactory = "userFactory")
	public void getData(UserDo meassage) {
		System.out.println("Json Message:" + meassage);
	}
	
}
