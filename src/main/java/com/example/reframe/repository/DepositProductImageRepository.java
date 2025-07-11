package com.example.reframe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.reframe.entity.DepositProductImage;

@Repository
public interface DepositProductImageRepository extends JpaRepository<DepositProductImage, Long> {
    DepositProductImage findFirstByProductIdAndType(Long productId, String type);
}
