package com.example.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shop.entity.Product;
import com.example.shop.repository.IProductRepository;

@Service
public class ProductService {
	
	@Autowired
    private IProductRepository pro;
	
	public List<Product> getAllProducts() {
		
        return pro.findAll();
    }
}
