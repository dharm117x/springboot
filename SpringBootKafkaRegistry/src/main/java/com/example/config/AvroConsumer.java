package com.example.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.example.data.Order;

@Service
public class AvroConsumer {
	final static Logger logger = Logger.getLogger(AvroProducer.class);

	public Map<String, Object> readMsg(Properties properties, String topic) {
		Map<String, Object> map = new HashMap<String, Object>();

		try (KafkaConsumer<String, Order> consumer = new KafkaConsumer<>(properties)) {
			consumer.subscribe(Collections.singletonList(topic));
			while (true) {
				ConsumerRecords<String, Order> records = consumer.poll(100);
				for (ConsumerRecord<String, Order> record : records) {
					String key = record.key();
					Order value = record.value();
					map.put(key, value);
					System.out.println(key + "------" + value);
				}
			}
		} catch (Exception e) {
			throw e;
		}

	}
}
