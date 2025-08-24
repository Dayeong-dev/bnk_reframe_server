// src/main/java/com/example/reframe/entity/ProductReview.java
package com.example.reframe.entity;

import java.util.Date;
import com.example.reframe.entity.auth.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(
  name = "product_review",
  indexes = {
    @Index(name = "idx_product_review_product", columnList = "PRODUCT_ID"),
    @Index(name = "idx_product_review_created_at", columnList = "CREATED_AT"),
    @Index(name = "idx_product_review_negative", columnList = "NEGATIVE")
  }
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductReview {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "PRODUCT_ID")
    private DepositProduct product;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "USER_ID")
    private User user;

    @NotBlank
    @Column(nullable = false, length = 1000)
    private String content;

    @Min(1) @Max(5) // 필요 없으면 제거
    private Integer rating; // 선택

    @Column(name = "NEGATIVE", nullable = false)
    private boolean negative;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT", nullable = false)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) this.createdAt = new Date();
    }

    // === 안전 접근 헬퍼(선택) ===
    @Transient
    public Long getProductIdSafe() { return product != null ? product.getProductId() : null; }

    @Transient
    public String getProductNameSafe() { return product != null && product.getName()!=null ? product.getName() : ""; }

    @Transient
    public String getUsernameSafe() { return user != null && user.getUsername()!=null ? user.getUsername() : "익명"; }
}
