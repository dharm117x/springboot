package com.example.config;

public class Center {

	
	String center_id;
	String min_age_limit;
	String name;
	String address;
	String state_name;
	String district_name;
	String available_capacity;
	String available_capacity_dose1;
	String fee;
	String vaccine;
	
	public String getCenter_id() {
		return center_id;
	}
	public void setCenter_id(String center_id) {
		this.center_id = center_id;
	}
	public String getMin_age_limit() {
		return min_age_limit;
	}
	public void setMin_age_limit(String min_age_limit) {
		this.min_age_limit = min_age_limit;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getState_name() {
		return state_name;
	}
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	public String getDistrict_name() {
		return district_name;
	}
	public void setDistrict_name(String district_name) {
		this.district_name = district_name;
	}
	public String getAvailable_capacity() {
		return available_capacity;
	}
	public void setAvailable_capacity(String available_capacity) {
		this.available_capacity = available_capacity;
	}
	public String getAvailable_capacity_dose1() {
		return available_capacity_dose1;
	}
	public void setAvailable_capacity_dose1(String available_capacity_dose1) {
		this.available_capacity_dose1 = available_capacity_dose1;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getVaccine() {
		return vaccine;
	}
	public void setVaccine(String vaccine) {
		this.vaccine = vaccine;
	}
	
	@Override
	public String toString() {
		return "Center [center_id=" + center_id + ", min_age_limit=" + min_age_limit + ", name=" + name + ", address="
				+ address + ", state_name=" + state_name + ", district_name=" + district_name + ", available_capacity="
				+ available_capacity + ", available_capacity_dose1=" + available_capacity_dose1 + ", fee=" + fee
				+ ", vaccine=" + vaccine + "]";
	}
	
}
