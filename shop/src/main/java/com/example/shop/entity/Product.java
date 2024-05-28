package com.example.shop.entity;

import java.time.LocalDateTime;
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
import jakarta.persistence.PrePersist;
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
@Table(name = "products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pid", nullable = false)
	private Long pid; // 기본키
	
	@Column(name = "pname", nullable = false)
    private String pname;
	
	private String description;
	
	@Column(nullable = false)
    private String price;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @Column(nullable = false)
    private String status;  // 'AVAILABLE' or 'SOLD_OUT'

    @Column(name = "pcreated_at", nullable = false, updatable = false)
    private LocalDateTime pcreatedAt; // 등록 시간
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Set<com.example.shop.entity.Comment> comments; // 댓글 목록
	
    @PrePersist
    protected void onCreate() {
    	pcreatedAt = LocalDateTime.now();
    }
    
}
