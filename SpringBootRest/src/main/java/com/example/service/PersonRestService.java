package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.data.Person;

@Service
public class PersonRestService {

	static List<Person> datas = new ArrayList<>();
	static{
		Person p = new Person();
		p.setName("Dharmendra");
		p.setEmail("dk@gmail.com");
		datas.add(p);
	}
	
	public List<Person> getAll() {
		return datas;
	}
	
	public List<Person> addPerson(Person p) {
		datas.add(p);
		return datas;
	}
}
