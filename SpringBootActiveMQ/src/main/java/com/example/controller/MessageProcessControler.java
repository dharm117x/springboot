package com.example.controller;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.process.MessageReceiver;
import com.example.process.MessageSender;

@RestController
public class MessageProcessControler {
	
	@Autowired
	MessageSender sender;
	@Autowired
	MessageReceiver receiver;
	
	@GetMapping("/produce/{message}")
	public String produce(@PathVariable String message) {
		sender.sendMessage(message);
		
		return "Message produced";
	}

	@GetMapping("/receiver")
	public String receiver() {
		try {
			return receiver.receiveMessage();
		} catch (MessageConversionException | JMSException e) {
			e.printStackTrace();
			return "Not Received!!";
		}
	}
	
}
