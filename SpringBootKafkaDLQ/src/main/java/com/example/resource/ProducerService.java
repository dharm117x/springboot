package com.example.resource;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Payment;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/kafka")
public class ProducerService {

	@Resource
	private KafkaTemplate<String, Payment> kafkaTemplate;


	public void send(String topic, String payRef) {
		kafkaTemplate.send(topic, Payment.create(payRef));
	}

	@GetMapping("/publish/paymnet/{ref}")
	public String postPayment(@PathVariable String ref) {
		kafkaTemplate.send("payments", Payment.create(ref));
		return "Payment Message post sucessed";
	}

}
