package com.example.reframe.repository.deposit;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.reframe.entity.ProductApplication;
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

	Optional<DepositPaymentLog> findByApplicationIdAndRound(Long appId, Integer round);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select l from DepositPaymentLog l where l.application.id=:appId and l.round=:round")
	Optional<DepositPaymentLog> findByApplicationIdAndRoundForUpdate(@Param("appId") Long appId,
                                                                   @Param("round") Integer round);

	@Query("select coalesce(sum(l.walkBonusApplied), 0) " +
			"from DepositPaymentLog l where l.application.id=:appId and l.walkConfirmedYn='Y'")
	BigDecimal sumConfirmedBonus(@Param("appId") Long appId);
}
