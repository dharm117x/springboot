package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.data.Person;
import com.example.service.PersonRestService;

@RestController
@RequestMapping("/rest")
public class PersonRestController {

	@Autowired
	PersonRestService service;

	@RequestMapping(value = "/persons", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Person> getPersons() {
		return service.getAll();
	}
	
	@PostMapping(path = "/addPerson", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<Person> addPerson( Person p) {
		return service.addPerson(p);
	}

	
}
