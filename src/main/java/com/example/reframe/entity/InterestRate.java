package com.example.reframe.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "INTEREST_RATE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterestRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private String category;     // ex: "만기지급", "중도해지", "만기후"
    private String type;         // ex: "12개월", "6개월미만" 등
    private Double rate;         // 연이율
    private Double annualReturn; // 연수익률 (nullable)
    private String note;         // 비고 (nullable)
}
