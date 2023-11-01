package com.example.model;

import java.io.Serializable;

public class UserDo implements Serializable {

	private static final long serialVersionUID = 1L;

	String name;
	String email;
	Long phone;

	public UserDo() {
	}
	
	public UserDo(String name, String email, Long phone) {
		this.name = name;
		this.email = email;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

}
