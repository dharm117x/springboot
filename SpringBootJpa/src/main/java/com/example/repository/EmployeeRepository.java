package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.person.Employee;

public interface EmployeeRepository  extends JpaRepository<Employee, Integer>{

}
