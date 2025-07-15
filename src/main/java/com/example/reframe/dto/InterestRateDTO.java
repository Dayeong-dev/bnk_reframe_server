package com.example.reframe.dto;

import lombok.*;

@Data
@Builder
public class InterestRateDTO {
    private Long id;
    private Long productId;
    private String category;
    private String type;
    private Double rate;
    private Double annualReturn;
    private String note;
}
