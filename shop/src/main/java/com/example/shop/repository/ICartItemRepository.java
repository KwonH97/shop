package com.example.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.shop.entity.CartItem;

public interface ICartItemRepository extends JpaRepository<CartItem, Long>{

	@Query(value="select * from cart_item where pid = :pid and username= :username" ,nativeQuery=true)
	Optional<CartItem> findByProductIdAndUsername(@Param("pid") Long pid, @Param("username") String username);
	
	
}
