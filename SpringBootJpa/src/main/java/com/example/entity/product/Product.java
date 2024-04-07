package com.example.entity.product;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // single table with discriminator column.
//@DiscriminatorColumn(name="type", discriminatorType=DiscriminatorType.INTEGER, length=20)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // Create separate table for each class with parent column.
//@Inheritance(strategy = InheritanceType.JOINED) // Create three table and join subcalss with primary key of parent.
public class Product {

	@Id
	Integer id;
	String name;
	String brand;
	String type;
	
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

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", brand=" + brand + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
