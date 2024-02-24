package com.example.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.product.ProductRepository;
import com.example.model.product.Product;

@RestController
@RequestMapping("/rest")
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private ProductRepository repository;

	@GetMapping("/products")
	public List<Product> getProducts() {
		
		return repository.findAll();
	}
	
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping(value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String addProduct(@Valid @RequestBody Product p) {
		logger.info("Product: {}", p);
		repository.save(p);
		return "Success";
	}

	
	
}
