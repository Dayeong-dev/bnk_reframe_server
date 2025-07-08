package com.example.reframe.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepositProductDTO {
    private Long productId;
    private String name;
    private String category;
    private String purpose;
    private String summary;
    private String detail;
    private BigDecimal maxRate; // 변경
    private BigDecimal minRate; // 변경
    private Integer period;
    private String status;
    private String createdAt;
    private Long viewCount;
    
    private String imageUrl; // 썸네일 이미지 URL

    

}

