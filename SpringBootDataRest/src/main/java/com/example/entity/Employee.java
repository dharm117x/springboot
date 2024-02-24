package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Columns;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	
	@Column(name = "email")
	private String email;
}
