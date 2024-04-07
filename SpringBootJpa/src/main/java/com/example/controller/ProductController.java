package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.product.Pen;
import com.example.entity.product.Phone;
import com.example.entity.product.Product;
import com.example.service.ProductService;

@RequestMapping("/api")
@RestController
public class ProductController {

	@Autowired
	ProductService service;

	@GetMapping("/product/id/{id}")
	public Product getEmployee(Integer id) {
		return service.getProduct(id).orElseThrow(() -> new RuntimeException("User not found"));
	}

	@GetMapping("/product/all")
	public List<Product> getAll() {
		return service.getAllProduct();
	}

	@PostMapping("/product/add")
	public String create(Product pc) {
		if ("pen".equals(pc.getType())) {
			Pen p = new Pen();
			p.setName("royle");
			p.setBrand("TEsla");
			p.setColor("Red");
			service.crateProduct(p);
		} else if ("phone".equals(pc.getType())) {
			Phone p = new Phone();
			p.setName("royle");
			p.setBrand("TEsla");
			p.setPrice(2000.90);
			service.crateProduct(p);
		} else {
			return "Not saved";
		}
		return "success";
	}
}
