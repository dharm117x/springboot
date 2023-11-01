package com.example;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;

@SpringBootApplication
public class SpringBootKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootKafkaApplication.class, args);
	}

	//@Bean
	public ApplicationRunner runner(KafkaTemplate<String, String> template) {
		return args -> {
			template.send("topic1", "test-data");
		};
	}

	//@Bean
	public RecordMessageConverter converter() {
		return new JsonMessageConverter();
	}

	//@Bean
	public NewTopic topic() {
		return TopicBuilder.name("topic1").partitions(10).replicas(1).build();
	}

	//@KafkaListener(id = "myId", topics = "topic1")
	public void listen(String in) {

		System.out.println("Test topic1::-> " + in);
	}

}
