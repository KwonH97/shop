package com.example.shop.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "Member")
public class Member {

    @Id
    @Column(name = "username", nullable = false, unique = true)
    private String username; // 아이디 (기본 키)

    @Column(name = "name", nullable = false, unique = true)
    private String name;	// 유저이름

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role")
    private String role;  // Role_Member Role_ADMIN

    @Column(name = "tel", nullable = false)
    private String tel;  
    
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id")
//    private Set<Comment> comments; // 댓글 목록
}