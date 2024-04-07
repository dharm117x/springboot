package com.example.entity.user;

import java.io.Serializable;

public class Name implements Serializable{
	private static final long serialVersionUID = 1L;
	String firsrtName;
	String lastName;

	public Name(String firsrtName, String lastName) {
		this.firsrtName = firsrtName;
		this.lastName = lastName;
	}

	public static String name(String firsrtName, String lastName) {

		return firsrtName + ":" + lastName;
	}

	public String getFirsrtName() {
		return firsrtName;
	}

	public String getLastName() {
		return lastName;
	}
	
	

}
