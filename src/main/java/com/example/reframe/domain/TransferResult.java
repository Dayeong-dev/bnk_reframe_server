package com.example.reframe.domain;

import java.time.LocalDateTime;

public record TransferResult(
	String correlationId,
	Long debitTransactionId,	// 출금 분개 ID
	Long creditTransactionId,	// 입금 분개 ID
	Long fromBalanceAfter,
	Long toBalanceAfter,
	LocalDateTime occurredAt
) {}
