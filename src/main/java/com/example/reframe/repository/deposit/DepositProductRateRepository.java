package com.example.reframe.repository.deposit;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.deposit.DepositProductRate;

public interface DepositProductRateRepository extends JpaRepository<DepositProductRate, Long> {

	List<DepositProductRate> findAllByProduct_ProductIdOrderByFromMonthAsc(Long productId);

	Optional<DepositProductRate> findTopByProduct_ProductIdAndFromMonthLessThanEqualAndToMonthGreaterThanEqualOrderByFromMonthDesc(
			Long productId, Integer months, Integer months2);

}
