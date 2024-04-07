package com.example.entity.product;

import javax.persistence.Entity;

@Entity
//@DiscriminatorValue(value = "1")
public class Pen extends Product {

	String color;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Pen [color=" + color + "]";
	}


}
