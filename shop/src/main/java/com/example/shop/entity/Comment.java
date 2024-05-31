package com.example.shop.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cid", nullable = false)
    private Long cid; // 기본키

    @ManyToOne
    @JoinColumn(name = "pid", nullable = false)
    private Product product; // 상품과의 관계

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private Member member; // 사용자와의 관계

    @Column(nullable = false)
    private String content; // 댓글 내용

    @Column(name = "ccreated_at", nullable = false, updatable = false)
    private LocalDateTime ccreatedAt; // 생성 시간

    @Column(name = "cupdated_at", nullable = false)
    private LocalDateTime cupdatedAt; // 수정 시간

    @PrePersist
    protected void onCreate() {
        ccreatedAt = LocalDateTime.now();
        cupdatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        cupdatedAt = LocalDateTime.now();
    }
}
