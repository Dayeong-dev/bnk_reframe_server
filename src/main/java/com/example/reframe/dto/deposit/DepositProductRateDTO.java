package com.example.reframe.dto.deposit;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DepositProductRateDTO {
	private Long id;		// 금리 ID
	
	private Long productId;		// 예적금 상품 ID (deposit_product)
	
	private Integer fromMonth;		// 금리 적용 시작 개월
	
	private Integer toMonth;	// 금리 적용 종료 개월
	
	private Float rate;		// 적용 금리
	
	private LocalDate createdAt;	// 생성일시
}
