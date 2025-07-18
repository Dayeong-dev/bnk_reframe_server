package com.example.reframe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "bnk_document")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Document extends BaseEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer documentId;		// 문서 아이디(자동증가)
	private String title;			// 저장 이름
	private String filename;		// 서버 저장 파일명
	private String productType;		// 상품 타입: 예적금, 카드
	@Size(min = 1, max = 1)
	private String documentType;	// 문서 타입: 약관(T), 상품설명서(M)
}
