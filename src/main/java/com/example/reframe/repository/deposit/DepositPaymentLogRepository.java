package com.example.reframe.repository.deposit;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.reframe.entity.deposit.DepositPaymentLog;
import com.example.reframe.entity.deposit.PaymentStatus;

import jakarta.persistence.LockModeType;

public interface DepositPaymentLogRepository extends JpaRepository<DepositPaymentLog, Long> {
	boolean existsByApplication_IdAndRound(Long applicationId, Integer round);
	
	List<DepositPaymentLog> findByApplication_Id(Long applicationId, Sort sort);

	List<DepositPaymentLog> findAllByApplication_IdOrderByRoundAsc(Long id);

	List<DepositPaymentLog> findTop30ByApplication_IdOrderByRoundDesc(Long applicationId);

	Optional<DepositPaymentLog> findFirstByApplication_IdAndStatusOrderByRoundAsc(Long id, PaymentStatus unpaid);
	
	// 가장 이른 UNPAID 1건을 for update
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<DepositPaymentLog> findTop1ByApplication_IdAndStatusOrderByRoundAsc(
	    Long applicationId, PaymentStatus status);
}
