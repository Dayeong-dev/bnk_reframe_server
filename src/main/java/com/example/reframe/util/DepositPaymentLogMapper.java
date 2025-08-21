package com.example.reframe.util;

import com.example.reframe.dto.deposit.DepositPaymentLogDTO;
import com.example.reframe.entity.deposit.DepositPaymentLog;

public class DepositPaymentLogMapper {
	ProductApplicationMapper productApplicationMapper = new ProductApplicationMapper();
	
	public DepositPaymentLog toEntity(DepositPaymentLogDTO paymentLogDTO) {
		DepositPaymentLog paymentLog = new DepositPaymentLog();
		
		paymentLog.setId(paymentLogDTO.getId());
		paymentLog.setApplication(productApplicationMapper.toEntity(paymentLogDTO.getApplication()));
		paymentLog.setPaidAt(paymentLogDTO.getPaidAt());
		paymentLog.setAmount(paymentLogDTO.getAmount());
		paymentLog.setRound(paymentLogDTO.getRound());
		paymentLog.setStatus(paymentLogDTO.getStatus());
		
		return paymentLog;
	}
	
	public DepositPaymentLogDTO toEntity(DepositPaymentLog paymentLog) {
		DepositPaymentLogDTO paymentLogDTO = new DepositPaymentLogDTO();
		
		paymentLogDTO.setId(paymentLog.getId());
		paymentLogDTO.setApplication(productApplicationMapper.toDTO(paymentLog.getApplication()));
		paymentLogDTO.setPaidAt(paymentLog.getPaidAt());
		paymentLogDTO.setAmount(paymentLog.getAmount());
		paymentLogDTO.setRound(paymentLog.getRound());
		paymentLogDTO.setStatus(paymentLog.getStatus());
		
		return paymentLogDTO;
	}
}
