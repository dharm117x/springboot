package com.example.config;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.example.data.Order;

@Service
public class AvroProducer {

	final static Logger logger = Logger.getLogger(AvroProducer.class);
	
	public Order sendMsg(Properties properties, String topic)
	{
	
		Order order = Order.newBuilder()
        		.setOrderId("OId234")
        		.setCustomerId("CId432")
        		.setSupplierId("SId543")
                .setItems(4)
                .setFirstName("Sunil")
                .setLastName("V")
                .setPrice(178f)
                .setWeight(75f)
                .build();

        ProducerRecord<String, Order> producerRecord = new ProducerRecord<String, Order>(topic, order.getOrderId(), order);
    	Producer<String, Order> producer = new KafkaProducer<String, Order>(properties);
        
        producer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception == null) {
                    logger.info(metadata); 
                } else {
                	logger.error(exception.getMessage());
                }
            }
        });

        producer.flush();
        producer.close();
        
        return order;
	}

}
