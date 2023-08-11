package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.solr.doc.Product;
import com.example.solr.service.ProductRestService;

@RestController
@RequestMapping("/rest")
public class ProductRestController {

	@Autowired
	ProductRestService service;

	@GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<Product> getProducts() {
		return service.getAllProducts();
	}
	
	@GetMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Product> getProductByName(@RequestParam String name) {
		return service.getByName(name);
	}
	
	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Product> search(@RequestParam(name = "text") String searchTerm) {
		return service.searchByCQ(searchTerm);
	}
	
	@GetMapping(value = "/search2", produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Product> search(@RequestParam(name = "text") String searchTerm, 
			@RequestParam(name = "currentPage") int page, 
			@RequestParam(name = "pageSize") int size, 
			@RequestParam(required = true) String sort) {
		
		return service.search(searchTerm, page, size, sort);
	}
	
	@PostMapping(path = "/addProduct", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public Product addPerson(@RequestBody Product p) {
		return service.addProduct(p);
	}
	
	@PutMapping(path = "/updateProduct")
	public String update(@RequestParam String id, @RequestParam String data) {
		return service.update(id, data);
	}
	
	

	
}
