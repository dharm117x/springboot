package com.example.process;

import javax.jms.Message;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import com.example.to.Article;

@Component
public class ReciverListener {

	@JmsListener(destination = "MESSASGE_QUEUE")
	public void receiveMessage(String message) {
		System.out.println("Received <" + message + ">");
	}

	@JmsListener(destination = "java")
	public void receiveJavaMessage(Article article) {
		System.out.println("Received: " + article);
	}

	@JmsListener(destination = "spring")
	@SendTo("review")
	public Article receiveSpringMessage(Article article) {
		article.setText("Spring JMS Tutorial");
		return article;
	}

	@JmsListener(destination = "review")
	public void reviewMessage(Article article) {
		System.out.println("Received: " + article);
	}
}
