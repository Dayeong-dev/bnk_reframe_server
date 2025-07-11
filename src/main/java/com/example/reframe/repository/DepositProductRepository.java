package com.example.reframe.repository;

import com.example.reframe.entity.DepositProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositProductRepository extends JpaRepository<DepositProduct, Long> {

    // ✅ 기본 제공: findById, findAll, save, delete 등

    // 🔍 목적별 상품 조회
    List<DepositProduct> findByPurpose(String purpose);

    // 🔍 카테고리별 상품 조회
    List<DepositProduct> findByCategory(String category);

    // 🔍 상태별 조회
    List<DepositProduct> findByStatus(String status);

    // 🔍 이름 LIKE 검색
    List<DepositProduct> findByNameContaining(String keyword);

    // 🔍 목적 + 상태별 조회
    List<DepositProduct> findByPurposeAndStatus(String purpose, String status);

    // 🔍 카테고리 + 상태별 조회
    List<DepositProduct> findByCategoryAndStatus(String category, String status);

    // ✅✅ ✅✅ ✅✅
    // 📌 📌 페이지네이션 + 정렬 + 검색용 추가 메서드
    // 상태 + 이름 포함 OR 상태 + 요약 포함
    Page<DepositProduct> findByStatusAndNameContainingOrStatusAndSummaryContaining(
            String status1, String name,
            String status2, String summary,
            Pageable pageable
    );

    // 상태 + 카테고리
    Page<DepositProduct> findByStatusAndCategory(
            String status,
            String category,
            Pageable pageable
    );

    // 상태만
    Page<DepositProduct> findByStatus(
            String status,
            Pageable pageable
    );

	List<DepositProduct> findTop10ByOrderByViewCountDesc();
}
