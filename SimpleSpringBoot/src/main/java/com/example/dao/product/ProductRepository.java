package com.example.dao.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.product.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> { }
