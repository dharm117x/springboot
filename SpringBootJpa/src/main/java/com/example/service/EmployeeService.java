package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.person.Employee;
import com.example.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository repository;
	
	
	public Optional<Employee> getEmployee(Integer id) {
		return repository.findById(id);
	}
	
	public List<Employee> getAllEmployee() {
		return repository.findAll();
	}
	
	public void crateEmployee(Employee p) {
		repository.save(p);
	}
	
	
}
