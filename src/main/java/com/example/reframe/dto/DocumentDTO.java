package com.example.reframe.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DocumentDTO {
    private Integer documentId;		// 문서 아이디(자동증가)
	private String title;			// 저장 이름
	private String filename;		// 서버 저장 파일명
	private String productType;		// 상품 타입: 예적금, 카드
	private String documentType;	// 문서 타입: 약관(T), 상품설명서(M)
	private LocalDateTime regDate;	// 생성일자
	private LocalDateTime modDate;	// 수정일자
}
