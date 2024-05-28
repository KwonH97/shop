package com.example.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shop.entity.Comment;

public interface ICommentRepository extends JpaRepository<Comment, Long>{

}
