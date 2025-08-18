// com.example.reframe.controller.ReviewController.java
package com.example.reframe.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reframe.dto.ReviewCreateDTO;
import com.example.reframe.dto.ReviewResponseDTO;
import com.example.reframe.service.ProductReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductReviewController {

    private final ProductReviewService productReviewService;

    // 실제 서비스에서는 SecurityContext에서 userId를 꺼내 사용
    private Long mockUserId() { return 1L; }

    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> list(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productReviewService.listByProduct(productId));
    }

    @PostMapping("/reviews")
    public ResponseEntity<Void> create(@RequestBody ReviewCreateDTO dto) {
        final Long id = productReviewService.create(mockUserId(), dto);
        return ResponseEntity.created(URI.create("/api/reviews/" + id)).build();
    }
}
