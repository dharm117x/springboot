package com.example.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

public class UserDto {
	@NotEmpty
	private Integer id;
	@NotBlank(message = "{name.not.blank}")
	@Size(min=2, message = "NAme should have 2 char")
	private String name;
	@Past
	private LocalDate dob;
	
	@NotBlank(message = "{username.not.blank}")
	private String username;
	private String token;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
