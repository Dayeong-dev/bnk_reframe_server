package com.example.reframe.repository.deposit;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.deposit.DepositPaymentLog;

public interface DepositPaymentLogRepository extends JpaRepository<DepositPaymentLog, Long> {
	boolean existsByApplication_IdAndRound(Long applicationId, Integer round);

	List<DepositPaymentLog> findAllByApplication_IdOrderByRoundAsc(Long id);
}
