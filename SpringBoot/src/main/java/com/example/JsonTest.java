package com.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.example.config.Center;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTest {
	static ObjectMapper mapper = new ObjectMapper();

	public static void main(String[] args) throws IOException {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		byte[] allBytes = Files.readAllBytes(new File("json2.data").toPath());
		String data = new String(allBytes);
		
		JSONObject obj = new JSONObject(data);
		Object object = obj.get("sessions");
		
		Map mapData = mapper.readValue(data, Map.class);

		System.out.println(mapData);
		
		List<Center> list = mapper.readValue(object.toString(), new TypeReference<List<Center>>() {
			
			 
		});
		
		for (Center center : list) {
			System.out.println(center);
		}

		
		/*
		 * List<Map> mapList = (List<Map>) mapData.get("sessions"); mapList.forEach(m->
		 * m.forEach((k, v) ->{ if(k.equals("min_age_limit")&& v.equals(18)) {
		 * System.out.println("Key : " + k + ", Value : " + v); }
		 * 
		 * }));
		 */
	
	}
}
