package com.example.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UserDto {
	private Integer id;
	@NotBlank(message = "{name.not.blank}")
	@Size(min=2, message = "NAme should have 2 char")
	private String firsrtName;
	@Size(min=2, message = "NAme should have 2 char")
	private String lastName;
	@Past
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	private Date dob;
	
	@NotBlank(message = "{username.not.blank}")
	private String username;
	private String password;
	
	private String token;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirsrtName() {
		return firsrtName;
	}

	public void setFirsrtName(String firsrtName) {
		this.firsrtName = firsrtName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	
}
