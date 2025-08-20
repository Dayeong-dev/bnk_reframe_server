package com.example.reframe.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class ProductListRowDTO {
    private Long id;           
    private String name;
    private String category;
    private BigDecimal minRate;
    private BigDecimal maxRate;
    private Integer period;

    private long total;
    private long started;
    private long closed;
    private long canceled;
}