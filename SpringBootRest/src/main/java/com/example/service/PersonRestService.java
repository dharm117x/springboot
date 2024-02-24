package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.example.data.Address;
import com.example.data.Person;

@Service
public class PersonRestService {

	static List<Person> datas = new ArrayList<>();
	static {
		Person p = new Person();
		p.setId(1);
		p.setName("Dharmendra");
		p.setEmail("dk@gmail.com");
		List<Address> address = new ArrayList<>();
		Address add1 = new Address();
		add1.setPin("909090");
		address.add(add1);
		p.setAddress(address);
		datas.add(p);
	}

	public List<Person> getAll() {
		return datas;
	}

	public List<Person> addPerson(Person p) {
		p.setId(datas.size() + 1);
		datas.add(p);
		return datas;
	}

	public Person updatePerson(@Valid Person person) {
		if (datas.stream().anyMatch(p -> p.equals(person))) {
			datas.stream().filter(p -> p.equals(person)).forEach(p -> {
				p.setName(person.getName());
			});
			
			System.out.println(datas);
		} else {
			throw new UserNotFoundException("User not found");
		}
		
		return person;
	}

	public Person deletePerson(@Valid Person p) {
		datas.remove(p);
		return p;
	}
}
