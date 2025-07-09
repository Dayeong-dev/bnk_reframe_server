package com.example.reframe.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page; // ✅ 정확한 Page 임포트
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reframe.dto.DepositProductDTO;
import com.example.reframe.entity.DepositProduct;
import com.example.reframe.entity.DepositProductImage;
import com.example.reframe.repository.DepositProductImageRepository;
import com.example.reframe.repository.DepositProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepositProductServiceImpl implements DepositProductService {

    private final DepositProductRepository depositProductRepository;
    private final DepositProductImageRepository depositProductImageRepository;

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }

    @Override
    public List<DepositProductDTO> getAllProducts(String status, String category) {
        List<DepositProduct> products;

        if (status != null && category != null) {
            products = depositProductRepository.findAll().stream()
                    .filter(p -> p.getStatus().equals(status) && p.getCategory().equals(category))
                    .collect(Collectors.toList());
        } else if (status != null) {
            products = depositProductRepository.findByStatus(status);
        } else if (category != null) {
            products = depositProductRepository.findByCategory(category);
        } else {
            products = depositProductRepository.findAll();
        }

        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<DepositProductDTO> searchProducts(String keyword) {
        List<DepositProduct> products = depositProductRepository.findByNameContaining(keyword);
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DepositProductDTO getProductDetail(Long productId) {
        DepositProduct product = depositProductRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + productId));

        // 조회수 증가
        product.setViewCount(product.getViewCount() == null ? 1 : product.getViewCount() + 1);

        return convertToDTO(product);
    }

    @Override
    public Long saveProduct(DepositProductDTO dto) {
        DepositProduct product = convertToEntity(dto);
        depositProductRepository.save(product);
        return product.getProductId();
    }

    @Override
    public Long updateProduct(Long productId, DepositProductDTO dto) {
        DepositProduct product = depositProductRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + productId));

        product.setName(dto.getName());
        product.setCategory(dto.getCategory());
        product.setPurpose(dto.getPurpose());
        product.setSummary(dto.getSummary());
        product.setDetail(dto.getDetail());
        product.setMaxRate(dto.getMaxRate());
        product.setMinRate(dto.getMinRate());
        product.setPeriod(dto.getPeriod());
        product.setStatus(dto.getStatus());

        depositProductRepository.save(product);
        return product.getProductId();
    }

    @Override
    public void deleteProduct(Long productId) {
        depositProductRepository.deleteById(productId);
    }

    @Override
    public List<DepositProductDTO> getProductsByPurpose(String purpose) {
        List<DepositProduct> products = depositProductRepository.findByPurposeAndStatus(purpose, "S");
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<DepositProductDTO> getRecommendedProducts() {
        List<DepositProduct> products = depositProductRepository.findByStatus("S");
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // ✅ 📌 추가: 페이지네이션 + 정렬 + 검색 통합 메서드
    @Override
    public Page<DepositProductDTO> getPagedProducts(String status, String category, String keyword, String sort, int page) {
        Pageable pageable;

        if ("rate".equals(sort)) {
            pageable = PageRequest.of(page, 8, Sort.by(Sort.Direction.DESC, "maxRate"));
        } else { // 기본 추천순
            pageable = PageRequest.of(page, 8, Sort.by(Sort.Direction.DESC, "viewCount"));
        }

        Page<DepositProduct> entityPage;

        if (keyword != null && !keyword.isBlank()) {
            entityPage = depositProductRepository.findByStatusAndNameContainingOrStatusAndSummaryContaining(
                    status, keyword, status, keyword, pageable
            );
        } else if (category != null && !category.isBlank()) {
            entityPage = depositProductRepository.findByStatusAndCategory(status, category, pageable);
        } else {
            entityPage = depositProductRepository.findByStatus(status, pageable);
        }

        return entityPage.map(this::convertToDTO);
    }

    // ===== DTO 변환 유틸 =====

    private DepositProductDTO convertToDTO(DepositProduct p) {
        DepositProductImage image = depositProductImageRepository.findFirstByProductIdAndType(p.getProductId(), "썸네일");
        String imageUrl = (image != null) ? image.getImageUrl() : "";

        return DepositProductDTO.builder()
                .productId(p.getProductId())
                .name(p.getName())
                .category(p.getCategory())
                .purpose(p.getPurpose())
                .summary(p.getSummary())
                .detail(p.getDetail())
                .maxRate(p.getMaxRate())
                .minRate(p.getMinRate())
                .period(p.getPeriod())
                .status(p.getStatus())
                .createdAt(formatDate(p.getCreatedAt()))
                .viewCount(p.getViewCount())
                .imageUrl(imageUrl)
                .build();
    }

    private DepositProduct convertToEntity(DepositProductDTO dto) {
        DepositProduct p = new DepositProduct();
        p.setName(dto.getName());
        p.setCategory(dto.getCategory());
        p.setPurpose(dto.getPurpose());
        p.setSummary(dto.getSummary());
        p.setDetail(dto.getDetail());
        p.setMaxRate(dto.getMaxRate());
        p.setMinRate(dto.getMinRate());
        p.setPeriod(dto.getPeriod());
        p.setStatus(dto.getStatus() != null ? dto.getStatus() : "S");
        p.setCreatedAt(new Date());
        p.setViewCount(0L);
        return p;
    }
}
