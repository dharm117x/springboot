package com.example.data;

import java.util.List;

public class Person {
	String name;
	String email;
	List<Address> address;

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

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", email=" + email + ", address=" + address + "]";
	}

	
}
