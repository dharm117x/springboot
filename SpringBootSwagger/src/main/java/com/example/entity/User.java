package com.example.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name = "sys_user")
@JsonPropertyOrder({ "id", "name", "email", "phone", "birthday", "commPreference" })
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotEmpty(message = "Name is required")
	private String name;

	@NotEmpty(message = "Email is required")
	@Email
	private String email;

	@NotEmpty(message = "Phone number is required")
	@Pattern(regexp = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message = "Mobile number is invalid")
	private String phone;

//	@Past
//	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate birthday;

	@NotEmpty(message = "Communication preference is required")
	private String commPreference;

	@ElementCollection
	private List<@NotEmpty String> mobileDevices;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getCommPreference() {
		return commPreference;
	}

	public void setCommPreference(String commPreference) {
		this.commPreference = commPreference;
	}

	public List<String> getMobileDevices() {
		return mobileDevices;
	}

	public void setMobileDevices(List<String> mobileDevices) {
		this.mobileDevices = mobileDevices;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + ", birthday=" + birthday
				+ ", commPreference=" + commPreference + ", mobileDevices=" + mobileDevices + "]";
	}

}
