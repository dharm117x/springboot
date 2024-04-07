package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.product.Product;
import com.example.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository repository;
	
	
	public Optional<Product> getProduct(Integer id) {
		return repository.findById(id);
	}
	
	public List<Product> getAllProduct() {
		return repository.findAll();
	}
	
	public void crateProduct(Product p) {
		repository.save(p);
	}

	
}
