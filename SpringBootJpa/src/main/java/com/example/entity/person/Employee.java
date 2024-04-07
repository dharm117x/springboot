package com.example.entity.person;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Employee extends Person {

	Double salary;
	@Temporal(TemporalType.DATE)
	Date dob;
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "paasport_id")
	Passport passport;
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "emp")
	List<Address> address;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "employee_bike", 
			joinColumns = @JoinColumn(name = "e_id", referencedColumnName = "id"), 
			inverseJoinColumns = @JoinColumn(name = "b_id", referencedColumnName = "id"))
	List<Bike> bikes;

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Passport getPassport() {
		return passport;
	}

	public void setPassport(Passport passport) {
		this.passport = passport;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public List<Bike> getBikes() {
		return bikes;
	}

	public void setBikes(List<Bike> bikes) {
		this.bikes = bikes;
	}

	@Override
	public String toString() {
		return "Employee [salary=" + salary + ", dob=" + dob + ", passport=" + passport + ", address=" + address
				+ ", bikes=" + bikes + "]";
	}

}
