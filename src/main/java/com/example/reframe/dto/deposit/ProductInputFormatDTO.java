package com.example.reframe.dto.deposit;

import com.example.reframe.dto.DepositProductDTO;
import com.example.reframe.entity.DepositProduct;

import lombok.Data;

@Data
public class ProductInputFormatDTO {
	private Long productId;		// 상품 ID

    private DepositProductDTO product;

    private Integer input1;		// 납입 기간 입력 여부
    
    private Integer input2;		// 납입/가입 금액 입력 여부
    
    private Integer input3;		// 이체일 입력 여부
    
    private Integer input4;		// 시작일 입력 여부
    
    private Integer input5;		// 만기 처리 방식 입력 여부
    
    private Integer input6;		// 비과세 여부 입력 여부

    private Integer input7;		// 모임명 입력 여부

    private Integer input8;		// 모임구분 입력 여부

    private Integer fromAccountReq;		// 출금계좌 입력 여부

    private Integer maturityAccountReq;		// 만기 시 입금계좌 필요 여부
}
