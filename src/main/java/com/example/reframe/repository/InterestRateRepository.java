package com.example.reframe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.InterestRate;

public interface InterestRateRepository extends JpaRepository<InterestRate, Long> {
   
    
    Optional<InterestRate> findFirstByProductId(Long productId);

}
