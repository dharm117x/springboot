package com.example.config;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class MyFirstTimerRouter extends RouteBuilder {

	@Autowired
	GetCurrentTimeBean getCurrentTimeBean;
	@Autowired
	SimpleLoggingProcess simpleLoggingProcess;
	@Override
	public void configure() throws Exception {
		from("timer:first-timeer")
		.log("${body}")
		.transform().constant("My message")
		.log("${body}")
		.bean(getCurrentTimeBean)
		.log("${body}")
		.bean(simpleLoggingProcess)
		.log("${body}")
		.process(new SimpleProcessor())
		.log("${body}")
		.to("log:Log-Timer");
	}
}

@Component
class GetCurrentTimeBean{
	public String getCuurentTime() {
		return "GetCurrentTimeBean -> "+LocalDateTime.now();
	}
}

@Component
class SimpleLoggingProcess{
	Logger log = LoggerFactory.getLogger(SimpleLoggingProcess.class);
	public void process(String msg) {
		log.info("SimpleLoggingProcess -> {}", msg);
	}
}

class SimpleProcessor implements Processor{
	Logger log = LoggerFactory.getLogger(SimpleLoggingProcess.class);
	@Override
	public void process(Exchange exchange) throws Exception {
		log.info("SimpleProcessor-> "+exchange.getMessage().getBody());
	}
}
