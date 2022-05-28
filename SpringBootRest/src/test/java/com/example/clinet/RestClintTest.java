package com.example.clinet;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.client.RestClintService;
import com.example.data.Person;
import com.example.data.PersonResponse;

@RunWith(MockitoJUnitRunner.class)
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class RestClintTest {
	
	@InjectMocks
	RestClintService restClintService;

	@Mock
	RestTemplate template;
	
	@Mock
	ResponseEntity<PersonResponse> responseEntity;

	HttpHeaders headers = new HttpHeaders();
	
	@Before
	public void setup() {
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList((MediaType.APPLICATION_JSON)));
	}

	@Test
	public void getAll_Test() {
		String url = "http://localhost:9090/rest/persons";
		HttpEntity requestEntity = new HttpEntity<String>("", headers);
		
		List<Person> list = new ArrayList();
		list.add(new Person());
		list.add(new Person());
		ResponseEntity<List> responseEntity = new ResponseEntity<List>(list, HttpStatus.OK);
		when(template.exchange(url, HttpMethod.GET, requestEntity, List.class))
				.thenReturn(responseEntity);
		List<Person> person = restClintService.getAllPerson();
	}

	@Test
	public void testGetPerson() {
		String url = "http://localhost:9090/rest/persons";
		
		HttpEntity requestEntity = new HttpEntity<String>("", headers);
		when(template.exchange(url, HttpMethod.GET, requestEntity, PersonResponse.class))
				.thenReturn(responseEntity);
	
		when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);

		PersonResponse personRsponse = new PersonResponse();
		personRsponse.setStatus("00");
		personRsponse.setPersons(Arrays.asList(new Person()));
		when(responseEntity.getBody()).thenReturn(personRsponse );
		
		PersonResponse person = restClintService.getPerson();
		assertNotNull(person);
		assertNotNull(person.getPersons());
		
	}

	@Test
	public void testNewGetPerson() {
		String url = "http://localhost:9090/rest/persons";
		when(template.exchange(ArgumentMatchers.anyString(), 
				ArgumentMatchers.any(HttpMethod.class), 
				ArgumentMatchers.any(),
				ArgumentMatchers.<Class<PersonResponse>>any()))
				.thenReturn(responseEntity);
	
		when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);

		PersonResponse personRsponse = new PersonResponse();
		personRsponse.setStatus("00");
		personRsponse.setPersons(Arrays.asList(new Person()));
		when(responseEntity.getBody()).thenReturn(personRsponse );
		
		PersonResponse person = restClintService.getPerson();
		assertNotNull(person);
		assertNotNull(person.getPersons());
		
	}
	
} 
