package com.example.reframe.service;

import com.example.reframe.dto.DepositProductDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.springframework.data.domain.Page;

import java.util.List;

public interface DepositProductService {

    // ✅ 전체 상품 목록 조회 (상태별/카테고리별 필터링)
    List<DepositProductDTO> getAllProducts(String status, String category);

    // ✅ 키워드 검색
    List<DepositProductDTO> searchProducts(String keyword);

    // ✅ 상품 상세 조회 (+ 조회수 증가)
    DepositProductDTO getProductDetail(Long productId) throws JsonMappingException, JsonProcessingException;
    
    // ✅ 상품 상세 조회(모바일) (+ 조회수 증가)
    DepositProductDTO getProductDetail2(Long productId) throws JsonMappingException, JsonProcessingException;

    // ✅ 관리자 상품 등록/수정/삭제
    Long saveProduct(DepositProductDTO dto);
    Long updateProduct(Long productId, DepositProductDTO dto);
    void deleteProduct(Long productId);

    // ✅ 목적별 추천 상품 목록
    List<DepositProductDTO> getProductsByPurpose(String purpose);

    // ✅ 강력추천 상품 목록
    List<DepositProductDTO> getRecommendedProducts();

    // ✅ 페이징 + 정렬 + 검색 통합 조회
    Page<DepositProductDTO> getPagedProducts(
            String status,
            String category,
            String keyword,
            String sort,
            int page
    );

    // ✅ 카테고리 기반 추천 (예금/적금/입출금자유)
    List<DepositProductDTO> getProductsByCategory(String category);

    // ✅ 테마 기반 추천 (직장인, 주부, 학생)
    List<DepositProductDTO> getThemeRecommended(String theme);
    
    List<String> findSuggestions(String keyword);
    
    
}
