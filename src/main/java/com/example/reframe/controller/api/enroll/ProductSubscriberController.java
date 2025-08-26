package com.example.reframe.controller.api.enroll;

import com.example.reframe.service.enroll.ProductApplicationQueryService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/enroll")
@CrossOrigin(origins = "*") // 필요 시 도메인 제한
public class ProductSubscriberController {

    private final ProductApplicationQueryService service;

    public ProductSubscriberController(ProductApplicationQueryService service) {
        this.service = service;
    }

    // 총 신청 건수
    // GET /api/enroll/products/{productId}/subscribers/count
    @GetMapping("/products/{productId}/subscribers/count")
    public Map<String, Object> countApplications(@PathVariable("productId") Long productId) {
        long total = service.countApplicationsByProductId(productId);
        return Map.of("productId", productId, "totalApplications", total);
    }

    // 고유 사용자 수 (USER_ID 중복 제거)
    // GET /api/enroll/products/{productId}/subscribers/distinct-count
    @GetMapping("/products/{productId}/subscribers/distinct-count")
    public Map<String, Object> countDistinctUsers(@PathVariable("productId") Long productId) {
        long distinctUsers = service.countDistinctUsersByProductId(productId);
        return Map.of("productId", productId, "distinctUsers", distinctUsers);
    }

    // 상태별 집계 (옵션)
    // GET /api/enroll/products/{productId}/subscribers/by-status
    @GetMapping("/products/{productId}/subscribers/by-status")
    public Map<String, Object> countByStatus(@PathVariable("productId") Long productId) {
        return Map.of(
            "productId", productId,
            "byStatus", service.countByStatus(productId)
        );
    }
}
