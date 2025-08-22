package com.example.reframe.repository.deposit;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.deposit.DepositPaymentLog;

public interface DepositPaymentLogRepository extends JpaRepository<DepositPaymentLog, Long> {
	boolean existsByApplication_IdAndRound(Long applicationId, Integer round);
}
