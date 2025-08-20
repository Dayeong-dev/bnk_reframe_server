package com.example.reframe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reframe.dto.AdminProductListItemDto;
import com.example.reframe.repository.ApplicationSummaryProjection;
import com.example.reframe.repository.DepositProductRepository;
import com.example.reframe.repository.ProductApplicationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminProductApplicationService {

    private final DepositProductRepository depositProductRepository;
    private final ProductApplicationRepository productApplicationRepository;

    // 좌측 상품 리스트
    public List<AdminProductListItemDto> getProducts() {
        return depositProductRepository.findProductsWithCounts();
    }

    // 우측 가입자 목록
    public List<Map<String, Object>> getApplications(Long productId) {
        // 프론트 테이블 스키마: 가입자/신청일/상태/상품계좌
        return productApplicationRepository.findByProductIdForAdmin(productId);
    }

    // 상단 요약지표: 없으면 프론트가 폴백 합산하지만, API로도 제공
    public Map<String, Long> getSummary() {
        ApplicationSummaryProjection p = productApplicationRepository.getSummaryCounts();
        Map<String, Long> m = new HashMap<>();
        m.put("total",     Optional.ofNullable(p.getTotal()).orElse(0L));
        // 프론트 키 이름에 맞춰서 progress/completed/canceled 로 내려줌
        m.put("progress",  Optional.ofNullable(p.getInProgress()).orElse(0L));
        m.put("completed", Optional.ofNullable(p.getCompleted()).orElse(0L));
        m.put("canceled",  Optional.ofNullable(p.getCanceled()).orElse(0L));
        return m;
    }

    private long n(Long v) { return v == null ? 0L : v; }
}