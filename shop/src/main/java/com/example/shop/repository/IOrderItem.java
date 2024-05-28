package com.example.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shop.entity.OrderItem;

public interface IOrderItem extends JpaRepository<OrderItem, Long>{

}
