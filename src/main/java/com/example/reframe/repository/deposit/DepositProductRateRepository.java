package com.example.reframe.repository.deposit;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.deposit.DepositProductRate;

public interface DepositProductRateRepository extends JpaRepository<DepositProductRate, Long> {

	List<DepositProductRate> findAllByProduct_ProductIdOrderByFromMonthAsc(Long productId);

}
