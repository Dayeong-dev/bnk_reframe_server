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
	private String categoryMajor;     // 카드 대분류 (ex: 개인(P), 기업(C), 체크(K))
	private String status;            // 신청 가능 상태 (ex: 가능(S), 대기(P), 불가(E))
	private String annualFee;         // 연회비
	private String service;           // 카드 서비스 설명 
//	private String issuedInfo;        // 카드 발급 정보
	private String pointInfo;         // 적립/할인 정보
	private Integer viewCount;        // 카드 조회수
	private String guideInfo;         // 상품 안내
//	private String onlinePaymentGuide;// 온라인 결제 안내
//	private String etcGuide;          // 기타 안내
	private Integer termId;			  // 이용 약관 ID
	private Integer manualId;		  // 상품 설명서 ID

    private List<String> subcategories; // 소분류명 리스트

    private List<BenefitItem> serviceList;
    
    private List<String> termImages;	// 약관 이미지 파일 리스트
    private List<String> manualImages;	// 상품 설명서 이미지 파일 리스트

 // categoryMajor 변환 메서드
    public String getCategoryMajorDisplay() {
        if (categoryMajor == null) return "알 수 없음";
        return switch (categoryMajor) {
            case "P" -> "개인";
            case "C" -> "기업";
            case "K" -> "체크";
            default -> "전체";
        };
    }

    // status 변환 메서드
    public String getStatusDisplay() {
        if (status == null) return "알 수 없음";
        return switch (status) {
            case "S" -> "신청 가능";
            case "P" -> "대기";
            case "E" -> "불가";
            default -> "신청 가능";
        };
    }
}
