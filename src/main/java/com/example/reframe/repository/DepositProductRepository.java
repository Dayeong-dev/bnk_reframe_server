package com.example.reframe.repository;

import com.example.reframe.entity.DepositProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositProductRepository extends JpaRepository<DepositProduct, Long> {

    // ✅ 기본 제공: findById, findAll, save, delete 등

    // 🔍 추천/목적별 상품 조회 예시
    List<DepositProduct> findByPurpose(String purpose);

    // 🔍 카테고리(예금/적금)별 상품 조회 예시
    List<DepositProduct> findByCategory(String category);

    // 🔍 상태별 조회 (판매중 등)
    List<DepositProduct> findByStatus(String status);

    // 🔍 이름 검색 포함 LIKE 예시
    List<DepositProduct> findByNameContaining(String keyword);
    
    List<DepositProduct> findByPurposeAndStatus(String purpose, String status);
   


}
