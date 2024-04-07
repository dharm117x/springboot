package com.example.entity.user;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

	private String streat;
	private String city;
	private String code;

	public String getStreat() {
		return streat;
	}

	public void setStreat(String streat) {
		this.streat = streat;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
