package com.example.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.data.Person;
import com.example.data.PersonResponse;

@Service
public class RestClintService {

	@Autowired
	RestTemplate template;

	public List<Person> getAllPerson() {
		List<Person> persons = null;
		String url = "http://localhost:9090/rest/persons";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList((MediaType.APPLICATION_JSON)));

		HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
		ResponseEntity<List> response = template.exchange(url, HttpMethod.GET, requestEntity, List.class);
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			persons = response.getBody();
		}

		persons.forEach(p -> System.out.print(p));
		return null;

	}

	public PersonResponse getPerson() {
		String url = "http://localhost:9090/rest/persons";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList((MediaType.APPLICATION_JSON)));

		try {
			HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
			ResponseEntity<PersonResponse> response = template.exchange(url, HttpMethod.GET, requestEntity,
					PersonResponse.class);
			if (response.getStatusCode().equals(HttpStatus.OK)) {
				return response.getBody();
			}else {
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;

	}
}
