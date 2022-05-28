package com.example.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.config.Center;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class VaccineService {
 final static Logger logger = LoggerFactory.getLogger(VaccineService.class);

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	EmailService emailService;
	
    //395 m, 392 thane
	String vaccInq = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin?pincode=400081&date=";
	String mumbai= "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByDistrict?district_id=395&date=";
	String thane= "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByDistrict?district_id=392&date=";
	
	String to  = "dharm117.mumbai@gmail.com";
	String subject = null;

	ObjectMapper mapper = new ObjectMapper();
	{
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	@Scheduled(cron = "*/60 * * * * *") // Every 60 seconds
	public void getVacineByPin() throws JsonMappingException, JsonProcessingException, MessagingException {
		System.out.println("Vaccine search by PIN");
		subject = "VACCINE-Hurry to login --> PIN";
		vaccineAlert(vaccInq);
	}
	
	@Scheduled(cron = "0 */30 * * * *") // Every 30 minute
	public void getVacineByDistM() throws JsonMappingException, JsonProcessingException, MessagingException {
		System.out.println("Vaccine search by DIST M");
		subject = "VACCINE-Hurry to login --> MUMBAI";
		vaccineAlert(mumbai);
	}
	
	@Scheduled(cron = "0 30 * * * *") // Every 30 minute
	public void getVacineByDistT() throws JsonMappingException, JsonProcessingException, MessagingException {
		System.out.println("Vaccine search by DIST T");
		subject = "VACCINE-Hurry to login --> THANE";
		
		vaccineAlert(thane);
	}
		
	public void vaccineAlert(String url) throws JsonMappingException, JsonProcessingException, MessagingException {
		LocalDateTime date = LocalDateTime.now();
		String formatDate = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		
		String jsonData = restTemplate.getForObject(url + formatDate, String.class);
	
		JSONObject obj = new JSONObject(jsonData);
		Object sessions = obj.get("sessions");
		
		List<Center> centers = mapper.readValue(sessions.toString(), new TypeReference<List<Center>>() {});
		List<Center> list = new ArrayList<>();
		
		for (Center center : centers) {
			if(Integer.valueOf(center.getAvailable_capacity()) > 0 && Integer.valueOf(center.getMin_age_limit()) ==18) {
				logger.info("Cneter infor:: " + center);
				list.add(center);
			}
		}
		
		logger.info("Vaccine Alert ON:: " + date + "  Total V::" + list.size());
		if(!list.isEmpty()) {
			Map<String, Object> templateModel = new HashMap<>();
			templateModel.put("centers", list);
			emailService.sendTemplateMessage(to, subject, templateModel );
			logger.info("Vaccine details mail sent.");
		}
			
	}


}
