package com.example.controller;

import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.config.AvroConsumer;
import com.example.config.AvroProducer;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

@RestController
public class DataController {

	final static Logger logger = Logger.getLogger(AvroProducer.class);
	
	@Value("${bootstrap.url}")
	String bootstrap;
	@Value("${registry.url}")
	String registry;
	
	@Autowired AvroProducer producer;
	@Autowired AvroConsumer consumer;
	
	@RequestMapping("/orders")
	public String sendMsg(@RequestParam(value="name", defaultValue="Order-avro") String name)
	{
		String ret=name;
		try
		{
			ret += "<br>Using Bootstrap : " + bootstrap;
			ret += "<br>Using Bootstrap : " + registry;
			
			Properties properties = new Properties();
			// Kafka Properties
			properties.setProperty("bootstrap.servers", bootstrap);
			properties.setProperty("acks", "all");
			properties.setProperty("retries", "10");

			// Avro properties
			properties.setProperty(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, registry);
			properties.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
			properties.setProperty("key.serializer", StringSerializer.class.getName());
			properties.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
			
			ret += producer.sendMsg(properties, name);
		}
		catch(Exception ex){ ret+="<br>"+ex.getMessage();}
		
		return ret;
	}
	
	@GetMapping("/read")
	public Map<String, Object> readMsg(@RequestParam(value="name", defaultValue="Order-avro") String name) {
		Map<String, Object> map = null;
		try {
			Properties properties = new Properties();
			// Kafka Properties
			properties.setProperty("bootstrap.servers", bootstrap);
			properties.setProperty("acks", "all");
			properties.setProperty("retries", "10");
			
			// Avro properties
			properties.setProperty(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, registry);
			properties.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
			
			properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
			properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
			
			map = consumer.readMsg(properties, name);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
}
