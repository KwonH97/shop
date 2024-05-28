package com.example.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shop.entity.Product;
import com.example.shop.repository.IProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {
	
	@Autowired
    private IProductRepository pro;
	
	public List<Product> getAllProducts() {
		
        return pro.findAll();
    }
	
	// 제품을 데이터베이스에 저장하는 메서드
    public void saveProduct(Product product) {
    	pro.save(product);
    }
    
    // 상품 삭제 로직을 구현한 메서드
    @Transactional
    public void deleteProduct(Long pid) {
    	pro.deleteById(pid); // ProductRepository의 deleteById 메서드를 사용하여 상품 삭제
    }
}
