package com.example.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.model.Employee;

public class EmployeeRowMapper implements RowMapper<Employee> {

	@Override
	public Employee mapRow(ResultSet rs, int c) throws SQLException {
		Employee emp = new Employee();
		emp.setId(rs.getString("id"));
		emp.setEmail(rs.getString("email"));
		emp.setName(rs.getString("name"));
		emp.setPhone(rs.getString("phone"));
		return emp;
	}

}
