package com.example.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shop.entity.Member;

public interface IMemberRepository extends JpaRepository<Member, String> {

    Member findByUserName(String userName);
	
}
