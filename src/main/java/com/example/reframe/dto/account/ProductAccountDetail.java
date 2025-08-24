package com.example.reframe.dto.account;

import java.util.List;

import com.example.reframe.dto.deposit.DepositPaymentLogDTO;
import com.example.reframe.dto.deposit.DepositProductRateDTO;
import com.example.reframe.dto.enroll.ProductApplicationDTO;

import lombok.Data;

@Data
public class ProductAccountDetail {
	private AccountDTO accountDTO;
	private ProductApplicationDTO applicationDTO;
	private List<DepositPaymentLogDTO> depositPaymentLogDTOList;
	private List<DepositProductRateDTO> productRateDTOList;
	
	private Long projectedInterestNow;      // 지금까지 발생 추정 이자(세전)
    private Long maturityAmountProjected;   // 만기 예상 수령액(세전, 원금+이자). 만기 없으면 null
}