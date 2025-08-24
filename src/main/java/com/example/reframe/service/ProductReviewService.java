package com.example.reframe.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reframe.dto.ReviewCreateDTO;
import com.example.reframe.dto.ReviewResponseDTO;
import com.example.reframe.dto.ReviewUpdateDTO;
import com.example.reframe.entity.DepositProduct;
import com.example.reframe.entity.ProductReview;
import com.example.reframe.entity.auth.User;
import com.example.reframe.netty.NettyPublisher;
import com.example.reframe.repository.DepositProductRepository;
import com.example.reframe.repository.ProductReviewRepository;
import com.example.reframe.repository.auth.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductReviewService {

    private final ProductReviewRepository productReviewRepository;
    private final DepositProductRepository depositProductRepository;
    private final UserRepository userRepository;
    private final OpenAIService openAIService;
    private final NettyPublisher nettyPublisher;

    @Transactional
    public Long create(Long userId, ReviewCreateDTO dto) {
        final DepositProduct product = depositProductRepository.findById(dto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품 없음"));
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        final String prompt = "다음 리뷰가 부정적인 리뷰인지 감지해줘. 부정이면 true, 아니면 false로만 답해줘: " + dto.getContent();
        final String result = openAIService.askChatGPT(prompt);
        final boolean isNegative = "true".equalsIgnoreCase(result.trim());

        final ProductReview review = ProductReview.builder()
                .product(product)
                .user(user)
                .content(dto.getContent())
                .rating(dto.getRating())
                .negative(isNegative)
                .build();

        productReviewRepository.save(review);

        // 실시간 브로드캐스트(요약/마스킹 포함)
        nettyPublisher.publish(
            "product." + dto.getProductId() + ".reviews",
            new PublicEvent(
                "review_created",
                dto.getProductId(),
                review.getId(),
                review.getRating(),
                maskName(user.getName()),
                snippet(review.getContent(), 20)
            )
        );

        // 부정 리뷰 관리자 알림
        if (isNegative) {
            nettyPublisher.publish("admin.alerts",
                new AdminEvent("review_negative", dto.getProductId(), review.getId(),
                    review.getContent(), user.getUsername()));
        }

        return review.getId();
    }

    @Transactional
    public void update(Long userId, Long reviewId, ReviewUpdateDTO dto) {
        ProductReview r = productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("review not found"));
        if (!r.getUser().getId().equals(userId)) {
            throw new SecurityException("forbidden");
        }
        if (dto.getContent() != null && !dto.getContent().trim().isEmpty()) {
            r.setContent(dto.getContent().trim());
        }
        if (dto.getRating() != null) {
            r.setRating(dto.getRating());
        }
        // JPA dirty checking 자동 반영
    }

    @Transactional
    public void delete(Long userId, Long reviewId) {
        ProductReview r = productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("review not found"));
        if (!r.getUser().getId().equals(userId)) {
            throw new SecurityException("forbidden");
        }
        productReviewRepository.delete(r);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDTO> listByProduct(Long productId, Long currentUserId) {
        return productReviewRepository
                .findAllByProductIdWithUser(productId)
                .stream()
                .map(r -> toDto(r, currentUserId))
                .toList();
    }

    // ✅ “내 리뷰” 목록
    @Transactional(readOnly = true)
    public List<ReviewResponseDTO> listMyReviews(Long userId) {
        return productReviewRepository.findAllByUserIdWithProduct(userId)
                .stream()
                .map(r -> toDtoWithProduct(r, userId))   // productName 포함 버전
                .toList();
    }

    // === DTO 매핑 ===
    private ReviewResponseDTO toDto(ProductReview r, Long currentUserId) {
        final Long authorId = (r.getUser() != null ? r.getUser().getId() : null);
        final boolean mine = (currentUserId != null && authorId != null && currentUserId.equals(authorId));

        return ReviewResponseDTO.builder()
                .id(r.getId())
                .productId(r.getProduct().getProductId())
                .authorId(authorId) // 프론트에서 toString() 처리 가능
                .authorName(r.getUser() != null ? r.getUser().getName() : "익명")
                .content(r.getContent())
                .rating(r.getRating())
                .createdAt(r.getCreatedAt())
                .mine(mine)
                .build();
    }

    private ReviewResponseDTO toDtoWithProduct(ProductReview r, Long currentUserId) {
        final Long authorId = (r.getUser() != null ? r.getUser().getId() : null);
        final boolean mine = (currentUserId != null && authorId != null && currentUserId.equals(authorId));

        return ReviewResponseDTO.builder()
                .id(r.getId())
                .productId(r.getProduct().getProductId())
                .productName(r.getProduct() != null ? r.getProduct().getName() : null) // 선택 필드
                .authorId(authorId)
                .authorName(r.getUser() != null ? r.getUser().getName() : "익명")
                .content(r.getContent())
                .rating(r.getRating())
                .createdAt(r.getCreatedAt())
                .mine(mine)
                .build();
    }

    // === 헬퍼 & 이벤트 DTO ===
    private static String maskName(String name) {
        if (name == null || name.isBlank()) return "고객";
        final String first = name.substring(0, 1);
        return first + "**";
    }
    private static String snippet(String s, int max) {
        if (s == null) return "";
        s = s.replaceAll("\\s+", " ").trim();
        return s.length() <= max ? s : s.substring(0, max) + "…";
    }

    public record PublicEvent(
            String type,
            Long productId,
            Long reviewId,
            Integer rating,
            String authorMasked,
            String contentSnippet
    ) {}

    public record AdminEvent(
            String type, Long productId, Long reviewId, String content, String username
    ) {}
}
