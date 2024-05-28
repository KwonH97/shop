package com.example.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shop.entity.CartItem;

public interface ICartItemRepository extends JpaRepository<CartItem, Long>{

	
}
