package com.example;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.example.model.UserDo;

@EnableKafka
@Configuration
public class KafkaConfig {


	private static final String LOCALHOST_9092 = "localhost:9092";

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	@Bean
	public ProducerFactory<String, String> producerFactory() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, LOCALHOST_9092);
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		
		return new DefaultKafkaProducerFactory<>(configs);
	}

	@Bean
	public KafkaTemplate<String, UserDo> userKafkaTemplate() {
		return new KafkaTemplate<>(userProducerFactory());
	}

	@Bean
	public ProducerFactory<String, UserDo> userProducerFactory() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, LOCALHOST_9092);
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		return new DefaultKafkaProducerFactory<>(configs);
	}


	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> factory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, LOCALHOST_9092);
		configs.put(ConsumerConfig.GROUP_ID_CONFIG, "group-string");
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		
		return new DefaultKafkaConsumerFactory<>(configs);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, UserDo> userFactory() {
		ConcurrentKafkaListenerContainerFactory<String, UserDo> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(userConsumerFactory());
		return factory;
	}

	@Bean
	public ConsumerFactory<String, UserDo> userConsumerFactory() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, LOCALHOST_9092);
		configs.put(ConsumerConfig.GROUP_ID_CONFIG, "group-json");
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		
		return new DefaultKafkaConsumerFactory<>(configs);
	}

}
