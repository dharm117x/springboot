package com.example.data;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class UserData implements Serializable {
	private static final long serialVersionUID = 1L;
	Long id;
	String name;

	public UserData() {
	}
	
	public UserData(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	@Override
	public String toString() {
		return "UserData [id=" + id + ", name=" + name + "]";
	}
	
	
	
	
}
