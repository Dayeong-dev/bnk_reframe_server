package com.example.reframe.dto.account;

import java.util.List;

import com.example.reframe.dto.deposit.DepositPaymentLogDTO;
import com.example.reframe.dto.deposit.DepositProductRateDTO;
import com.example.reframe.dto.enroll.ProductApplicationDTO;
import com.example.reframe.dto.enroll.ProductApplicationInputDTO;

import lombok.Data;

@Data
public class ProductAccountDetail {
	private AccountDTO accountDTO;
	private ProductApplicationDTO applicationDTO;
	private List<DepositPaymentLogDTO> depositPaymentLogDTOList;
	private List<DepositProductRateDTO> productRateDTOList;
}