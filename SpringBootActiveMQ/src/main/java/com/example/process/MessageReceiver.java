package com.example.process;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {

	@Autowired
	JmsTemplate jmsTemplate;
	@Autowired
	MessageConverter converter;

	public String receiveMessage() throws MessageConversionException, JMSException {
		Message message = jmsTemplate.receive();
		Object object = converter.fromMessage(message);
		return "Message Recived. :: " + object;
	}
}
