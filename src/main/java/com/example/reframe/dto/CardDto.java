package com.example.reframe.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDto {
	private Long cardId;              // 카드 고유 ID (PK, 자동증가)
	private String name;              // 카드 이름
	private String description;       // 카드 요약 설명
	private String tags;              // 해시태그 (ex: #생활/쇼핑)
	private String categoryMajor;     // 카드 대분류 (ex: 개인, 기업, 체크)
	private String status;            // 신청 가능 상태 (ex: 가능, 대기, 불가)
	private Integer annualFee;        // 연회비
	private String service;           // 카드 서비스 설명 
	private String issuedInfo;        // 카드 발급 정보
	private String pointInfo;         // 적립/할인 정보
	private Integer viewCount;        // 카드 조회수
	private String guideInfo;         // 상품 안내
	private String onlinePaymentGuide; // 온라인 결제 안내
	private String etcGuide;          // 기타 안내
	private String termsGuide;        // 상품 설명서 및 이용약관


    private List<String> subcategories; // 소분류명 리스트
}
