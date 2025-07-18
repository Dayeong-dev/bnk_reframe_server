package com.example.reframe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.DepositProductContent;

public interface DepositProductContentRepository extends JpaRepository<DepositProductContent, Integer> {


	List<DepositProductContent> findByDepositProduct_ProductIdOrderByContentOrderAsc(Long productId);

}
