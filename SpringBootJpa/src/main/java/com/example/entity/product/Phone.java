package com.example.entity.product;

import javax.persistence.Entity;

@Entity
//@DiscriminatorValue(value = "2")
public class Phone extends Product {

	Double price;

	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Phone [brand=" + brand + ", price=" + price + "]";
	}

}
