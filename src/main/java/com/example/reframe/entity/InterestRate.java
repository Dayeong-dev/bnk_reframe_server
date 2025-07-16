package com.example.reframe.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DEPOSIT_PRODUCT_RATE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterestRate {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RATE_ID") 
    private Long id;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "HTML_TABLE")
    @Lob
    private String htmlTable;
}
