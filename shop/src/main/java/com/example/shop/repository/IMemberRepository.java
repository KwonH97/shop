package com.example.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.shop.entity.Member;

public interface IMemberRepository extends JpaRepository<Member, String> {

//	@Query(value="select * from member where user_name= :userName", nativeQuery=true)
//    Optional<Member> findByUserName(@Param("userName") String userName);

	public Member findByUsername(String username);

}
