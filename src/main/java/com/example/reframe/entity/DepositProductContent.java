package com.example.reframe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="bnk_deposit_product_content")
@Data
public class DepositProductContent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer contentId;				// 예적금 상품 내용 ID
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_ID")
	private DepositProduct depositProduct;	// 예적금 상품 ID
	
	private Integer contentOrder;			// 순서
	
	private String title;			// 제목(h2에 해당)
	private String content;			// 내용(p에 해당)
	private String imageURL;		// 이미지 URL
}
