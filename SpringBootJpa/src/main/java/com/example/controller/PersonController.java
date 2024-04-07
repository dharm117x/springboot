package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.person.Employee;
import com.example.service.EmployeeService;

@RequestMapping("/api")
@RestController
public class PersonController {

	@Autowired
	EmployeeService service;
	
	@GetMapping("/person/id/{id}")
	public Employee getEmployee(Integer id) {
		return service.getEmployee(id).orElseThrow(() ->  new RuntimeException("User not found"));
	}
	
	@GetMapping("/person/all")
	public List<Employee> getAll() {
		return service.getAllEmployee();
	}
	
	@PostMapping("/person/add")
	public String create(Employee p) {
		
		service.crateEmployee(p);
		return "success";
	}
}
