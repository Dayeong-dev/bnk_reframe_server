package com.example.reframe.domain;

import java.time.LocalDateTime;

import com.example.reframe.entity.account.TransactionType;

import lombok.Data;

@Data
public class TransferCommand {
	String correlationId;	// 비즈니스 거래 식별자

	Long fromAccountId;	// 출금 계좌

	Long toAccountId;	// 입금(상품) 계좌

	Long amount;	// 이체 금액(양수)

	TransactionType transactionType;	// DEPOSIT_PAYMENT/TRANSFER/INTEREST_CREDIT 등
	
	LocalDateTime occurredAt;		// 거래 발생 시간

	String idempotencyKey;	// 재시도 시 중복 처리 방지
}
