package com.example.reframe.repository;

import com.example.reframe.entity.InterestRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestRateRepository extends JpaRepository<InterestRate, Long> {
    List<InterestRate> findByProductIdOrderByCategoryAscTypeAsc(Long productId);
}
