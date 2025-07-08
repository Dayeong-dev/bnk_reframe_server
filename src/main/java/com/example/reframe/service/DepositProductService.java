package com.example.reframe.service;

import com.example.reframe.dto.DepositProductDTO;

import java.util.List;

public interface DepositProductService {

    // 전체 상품 목록 조회 (상태별/카테고리별 필터링)
    List<DepositProductDTO> getAllProducts(String status, String category);

    // 키워드 검색
    List<DepositProductDTO> searchProducts(String keyword);

    // 상품 상세 조회 (+ 조회수 증가)
    DepositProductDTO getProductDetail(Long productId);

    // (추가) 관리자 상품 등록/수정/삭제 시 사용
    Long saveProduct(DepositProductDTO dto);
    Long updateProduct(Long productId, DepositProductDTO dto);
    void deleteProduct(Long productId);
    
    List<DepositProductDTO> getProductsByPurpose(String purpose);
    List<DepositProductDTO> getRecommendedProducts();


}
