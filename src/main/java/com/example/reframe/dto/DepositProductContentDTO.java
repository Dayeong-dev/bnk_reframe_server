package com.example.reframe.dto;

import com.example.reframe.entity.DepositProduct;

import lombok.Data;

@Data
public class DepositProductContentDTO {
	private Integer contentId;				// 예적금 상품 내용 ID
	private DepositProduct depositProduct;	// 예적금 상품 ID
	
	private Integer contentOrder;			// 순서
	
	private String title;					// 제목(h2)
	private String content;					// 내용(p)
	private String imageURL;				// 이미지 URL
}
