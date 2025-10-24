package com.example.reframe.repository.product.card;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.product.card.CardTestResult;

public interface CardTestResultRepository extends JpaRepository<CardTestResult, Long> {
    Optional<CardTestResult> findByResultType(String resultType);

}
