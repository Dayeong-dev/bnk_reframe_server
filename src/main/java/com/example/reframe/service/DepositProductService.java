package com.example.reframe.service;

import com.example.reframe.dto.DepositProductDTO;
import org.springframework.data.domain.Page;
import java.util.List;

public interface DepositProductService {

    List<DepositProductDTO> getAllProducts(String status, String category);
    List<DepositProductDTO> searchProducts(String keyword);
    DepositProductDTO getProductDetail(Long productId);

    Long saveProduct(DepositProductDTO dto);
    Long updateProduct(Long productId, DepositProductDTO dto);
    void deleteProduct(Long productId);

    // ✅ 목적별 추천 상품
    List<DepositProductDTO> getProductsByPurpose(String purpose);

    // ✅ 카테고리별 추천 상품
    List<DepositProductDTO> getProductsByCategory(String category);

    // ✅ 강력추천 상품
    List<DepositProductDTO> getRecommendedProducts();

    // ✅ 페이징+정렬+검색
    Page<DepositProductDTO> getPagedProducts(
            String status,
            String category,
            String keyword,
            String sort,
            int page
    );
	List<DepositProductDTO> getThemeRecommended(String theme);
}
