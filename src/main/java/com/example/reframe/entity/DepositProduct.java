package com.example.reframe.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DEPOSIT_PRODUCT")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepositProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "CATEGORY", nullable = false, length = 20)
    private String category;

    @Column(name = "PURPOSE", length = 20)
    private String purpose;

    @Column(name = "SUMMARY", length = 500)
    private String summary;

    @Lob
    @Column(name = "DETAIL")
    private String detail;

    @Column(name = "MAX_RATE", precision = 5, scale = 2)
    private BigDecimal maxRate;

    @Column(name = "MIN_RATE", precision = 5, scale = 2)
    private BigDecimal minRate;

    @Column(name = "PERIOD")
    private Integer period;

    @Column(name = "STATUS", length = 1)
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Column(name = "VIEW_COUNT")
    private Long viewCount;

    @Column(name = "IMAGE_URL", nullable = true)
    private String imageUrl;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
}
