// src/main/java/com/example/reframe/controller/ReviewApiController.java
package com.example.reframe.controller;

import com.example.reframe.auth.CurrentUser;
import com.example.reframe.dto.ReviewCreateDTO;
import com.example.reframe.dto.ReviewResponseDTO;
import com.example.reframe.dto.ReviewUpdateDTO;
import com.example.reframe.service.ProductReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/mobile")
@RequiredArgsConstructor
public class ReviewApiController {

    private final ProductReviewService productReviewService;
    private final CurrentUser currentUser;

    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> list(@PathVariable("productId") Long productId) {
        Long uid = requireUser();
        return ResponseEntity.ok(productReviewService.listByProduct(productId, uid));
    }

    @PostMapping("/reviews")
    public ResponseEntity<Void> create(@RequestBody ReviewCreateDTO dto) {
        Long uid = requireUser();
        Long id = productReviewService.create(uid, dto);
        return ResponseEntity.created(URI.create("/mobile/reviews/" + id)).build();
    }

    @PutMapping("/reviews/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id,
                                       @RequestBody ReviewUpdateDTO dto) {
        Long uid = requireUser();
        productReviewService.update(uid, id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Long uid = requireUser();
        productReviewService.delete(uid, id);
        return ResponseEntity.noContent().build();
    }

    // ✅ 내 리뷰 목록
    @GetMapping("/reviews/me")
    public ResponseEntity<List<ReviewResponseDTO>> myReviews() {
        Long uid = requireUser();
        return ResponseEntity.ok(productReviewService.listMyReviews(uid));
    }

    private Long requireUser() {
        Long id = currentUser.id();
        if (id == null) throw new IllegalStateException("Unauthorized");
        return id;
    }
}
