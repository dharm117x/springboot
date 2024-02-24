package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.entity.Employee;

@RepositoryRestResource(path = "emps")
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
