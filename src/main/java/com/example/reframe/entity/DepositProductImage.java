package com.example.reframe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "DEPOSIT_PRODUCT_IMAGE")
public class DepositProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMAGE_ID")
    private Long imageId;

    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;

    @Column(name = "IMAGE_URL", nullable = false, length = 500)
    private String imageUrl;

    @Column(name = "TYPE", length = 20)
    private String type; // 썸네일, 상세 등 구분

}
