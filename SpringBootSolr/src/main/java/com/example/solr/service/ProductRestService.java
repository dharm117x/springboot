package com.example.solr.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.solr.repository.ProductRepository;
import com.example.solr.doc.Product;

@Service
public class ProductRestService {

	@Autowired
	ProductRepository repository;

	public Iterable<Product> getAllProducts() {
		return repository.findAll();
	}

	public Product addProduct(Product p) {
		return repository.save(p);
	}

	public List<Product> getByName(String name) {
		return repository.findByName(name);
	}


	public Page<Product> search(String searchTerm, int page, int size, String sort) {
		PageRequest pageable = PageRequest.of(page, size, Sort.by(sort) );
		
		return repository.findByCustomQuery(searchTerm, pageable);
	}

	public Page<Product> searchByCQ(String searchTerm) {
		PageRequest pageable = PageRequest.of(0, 10);
		return repository.findByCustomQuery(searchTerm, pageable);
	}
	
	public Page<Product> searchByNQ(String searchTerm) {
		PageRequest pageable = PageRequest.of(0, 10);
		return repository.findByNamedQuery(searchTerm, pageable);
	}
	
	public String update(String id, String data) {
		Optional<Product> retrievedProduct = repository.findById(id);
		if (retrievedProduct.isPresent()) {
			Product product = retrievedProduct.get();
			product.setName(data);
			repository.save(product);
			return "update sucessfully"; 
		} else {
			return "prodcut not found";
		}
	}

	public void deleteById(String id) {
		repository.deleteById(id);
	}

}
