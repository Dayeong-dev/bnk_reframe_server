package com.example.reframe.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.reframe.dto.ApplicationRowDTO;
import com.example.reframe.dto.ProductApplicationsResponseDTO;
import com.example.reframe.dto.ProductListRowDTO;
import com.example.reframe.entity.DepositProduct;
import com.example.reframe.entity.ProductApplication;
import com.example.reframe.entity.ProductApplication.ApplicationStatus;
import com.example.reframe.repository.DepositProductRepository;
import com.example.reframe.repository.ProductApplicationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminProductApplicationService {

    private final DepositProductRepository depositRepo;
    private final ProductApplicationRepository appRepo;

    /** 좌측 목록: 판매중 상품 + 건수 집계 */
    public List<ProductListRowDTO> getProductListWithCounts() {
        List<DepositProduct> products = depositRepo.findActive();
        return products.stream().map(p -> {
            long total    = appRepo.countByProduct_ProductId(p.getProductId());
            long started  = appRepo.countByProduct_ProductIdAndStatus(p.getProductId(), ApplicationStatus.STARTED);
            long closed   = appRepo.countByProduct_ProductIdAndStatus(p.getProductId(), ApplicationStatus.CLOSED);
            long canceled = appRepo.countByProduct_ProductIdAndStatus(p.getProductId(), ApplicationStatus.CANCELED);
            return ProductListRowDTO.builder()
                    .id(p.getProductId())
                    .name(p.getName())
                    .category(p.getCategory())
                    .minRate(p.getMinRate())
                    .maxRate(p.getMaxRate())
                    .period(p.getPeriod())
                    .total(total).started(started).closed(closed).canceled(canceled)
                    .build();
        }).collect(Collectors.toList());
    }

    /** 우측 상세: 가입자 목록 + 통계 */
    public ProductApplicationsResponseDTO getApplications(Long productId) {
        List<ProductApplication> apps = appRepo.findAllByProductIdWithJoins(productId);

        DepositProduct p = apps.isEmpty() ? depositRepo.findById(productId).orElse(null) : apps.get(0).getProduct();

        long total    = appRepo.countByProduct_ProductId(productId);
        long started  = appRepo.countByProduct_ProductIdAndStatus(productId, ApplicationStatus.STARTED);
        long closed   = appRepo.countByProduct_ProductIdAndStatus(productId, ApplicationStatus.CLOSED);
        long canceled = appRepo.countByProduct_ProductIdAndStatus(productId, ApplicationStatus.CANCELED);
        long recent7d = appRepo.countByProduct_ProductIdAndStartAtAfter(productId, LocalDateTime.now().minusDays(7));

        List<ApplicationRowDTO> rows = apps.stream().sorted(Comparator.comparing(ProductApplication::getStartAt).reversed())
                .map(a -> ApplicationRowDTO.builder()
                        .applicationId(a.getId())
                        .status(a.getStatus().name())
                        .startAt(a.getStartAt())
                        .closeAt(a.getCloseAt())
                        .userId(a.getUser().getId())
                        .userName(a.getUser().getName())         // User 엔티티에 맞게 수정
                        .userPhone(a.getUser().getPhone())       // User 엔티티에 맞게 수정
                        .productAccountNumber(a.getProductAccount().getAccountNumber())
                        .productAccountBank(a.getProductAccount().getBankName())
                        .fromAccountNumber(a.getFromAccount().getAccountNumber())
                        .fromAccountBank(a.getFromAccount().getBankName())
                        .maturityAccountNumber(a.getMaturityAccount().getAccountNumber())
                        .maturityAccountBank(a.getMaturityAccount().getBankName())
                        .build())
                .collect(Collectors.toList());

        return ProductApplicationsResponseDTO.builder()
                .product(ProductApplicationsResponseDTO.ProductInfo.builder()
                        .id(p != null ? p.getProductId() : productId)
                        .name(p != null ? p.getName() : "")
                        .category(p != null ? p.getCategory() : "")
                        .minRate(p != null ? p.getMinRate() : null)
                        .maxRate(p != null ? p.getMaxRate() : null)
                        .period(p != null ? p.getPeriod() : 0)
                        .build())
                .counts(new ProductApplicationsResponseDTO.Counts(total, started, closed, canceled))
                .recent7d(recent7d)
                .applications(rows)
                .build();
    }
}