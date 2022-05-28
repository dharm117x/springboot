package com.example.dto;

import javax.validation.constraints.NotBlank;

public class UserDto {
	private Integer id;
	@NotBlank(message = "{name.not.blank}")
	private String name;
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
