package com.example.data;

import java.util.List;

public class PersonResponse {
private List<Person> persons;
private String status;
public List<Person> getPersons() {
	return persons;
}
public void setPersons(List<Person> persons) {
	this.persons = persons;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}


}
