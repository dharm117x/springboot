package com.example.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.data.Person;
import com.example.service.PersonRestService;

@RestController
@RequestMapping("/rest")
public class PersonRestController extends BaseController{

	@Autowired
	PersonRestService service;

	@RequestMapping(value = "/persons", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Person> getPersons() {
		return service.getAll();
	}
	
	@PostMapping(path = "/addPerson")
	public List<Person> addPerson(@RequestBody @Valid Person p) {
		return service.addPerson(p);
	}

	@PutMapping(value = "/updatePerson")
	public Person updatePerson(@RequestBody @Valid Person p) {
		return service.updatePerson(p);
	}
	
	@DeleteMapping(value = "/deletePerson")
	public Person deltePerson(@RequestBody @Valid Person p) {
		return service.deletePerson(p);
	}
	
}
