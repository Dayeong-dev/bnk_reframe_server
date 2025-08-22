// src/main/java/com/example/reframe/entity/ProductReview.java
package com.example.reframe.entity;

import java.util.Date;

import com.example.reframe.entity.auth.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_review")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductReview {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "PRODUCT_ID")
    private DepositProduct product;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "USER_ID")
    private User user;

    @Column(nullable = false, length = 1000)
    private String content;

    private Integer rating; // 선택

    @Column(name = "NEGATIVE", nullable = false)
    private boolean negative;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT")
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) this.createdAt = new Date();
    }
}
