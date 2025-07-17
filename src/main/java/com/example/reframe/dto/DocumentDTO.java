package com.example.reframe.dto;

import lombok.Data;

@Data
public class DocumentDTO {
    private Integer documentId;		// 문서 아이디(자동증가)
	private String title;			// 저장 이름
	private String filename;		// 서버 저장 파일명
	private String productType;		// 상품 타입: 예적금, 카드
	private String documentType;	// 문서 타입: 약관(T), 상품설명서(M)
}
