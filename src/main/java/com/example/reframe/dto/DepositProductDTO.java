package com.example.reframe.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepositProductDTO {
    
	private Long productId;      // 상품ID
    private String name;		 // 상품명
    private String category;	 // 상품 유형 (예금,적금)
    private String purpose;		 // 추천 대상 (주부, 학생 등)
    private String summary;      // 간단 요약 설명
    private String detail; 		 // 상세 설명
    private BigDecimal maxRate;  // 최고 금리
    private BigDecimal minRate;  // 최소 금리
    private Integer period;		 // 가입기간
    private String status;		 // 판매 상태
    private String createdAt;	 // 등록일
    private Long viewCount;  	 // 조회수
    
    private String imageUrl;     // 썸네일 이미지 URL

    

}

