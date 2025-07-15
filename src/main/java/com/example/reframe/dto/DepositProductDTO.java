package com.example.reframe.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepositProductDTO {

    private Long productId;       // 상품ID
    private String name;          // 상품명
    private String category;      // 상품 유형 (예금, 적금 등)
    private String purpose;       // 추천 대상 (직장인, 주부 등)
    private String summary;       // 간단 요약 설명
    private String detail;        // 상세 설명 (HTML)
    private String modalDetail;   // 모달용 요약 설명 (HTML)
    private BigDecimal maxRate;   // 최고 금리
    private BigDecimal minRate;   // 최소 금리
    private Integer period;       // 가입 기간 (개월 수)
    private String status;        // 판매 상태 (S:판매중, E:판매종료 등)
    private String createdAt;     // 등록일 (문자열 변환된 날짜)
    private Long viewCount;       // 조회수
    private String imageUrl;      // 썸네일 이미지 URL
}
