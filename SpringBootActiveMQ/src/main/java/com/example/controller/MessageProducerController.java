package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.to.Article;

@RestController
public class MessageProducerController {

	@Autowired
	JmsTemplate jmsTemplate;
	
	@GetMapping("/produce")
	public String messageProduce() {
		Article javaArticle = new Article(101, "Java JMS Tutorial");
		jmsTemplate.convertAndSend("java", javaArticle);

		Article springArticle = new Article(102);
		jmsTemplate.convertAndSend("spring", springArticle);
		
		return "Message Produce succfully";
	}
}
