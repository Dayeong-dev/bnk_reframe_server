package com.example.reframe.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reframe.dto.ReviewCreateDTO;
import com.example.reframe.dto.ReviewResponseDTO;
import com.example.reframe.entity.DepositProduct;
import com.example.reframe.entity.ProductReview;
import com.example.reframe.entity.auth.User;          // ← ProductUser면 변경
import com.example.reframe.netty.NettyPublisher;
import com.example.reframe.repository.DepositProductRepository;
import com.example.reframe.repository.ProductReviewRepository;
import com.example.reframe.repository.auth.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductReviewService {

    private final ProductReviewRepository productReviewRepository;
    private final DepositProductRepository depositProductRepository;
    private final UserRepository userRepository;
    private final OpenAIService openAIService;        // 네가 이미 사용하던 서비스
    private final NettyPublisher nettyPublisher;      // 내장 Netty 브로커 퍼블리셔

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
                .rating(dto.getRating())   // ★ 평점 저장
                .negative(isNegative)
                .build();
        productReviewRepository.save(review);

        // ★ 모두에게 브로드캐스트 (마스킹/요약 포함)
        nettyPublisher.publish("product." + dto.getProductId() + ".reviews",
                new PublicEvent(
                        "review_created",
                        dto.getProductId(),
                        review.getId(),
                        review.getRating(),
                        maskName(user.getName()),
                        snippet(review.getContent(), 20)   // ‘금리 혜택 만족해요’ 같은 요약
                ));

        // 부정 리뷰는 관리자 알림
        if (isNegative) {
            nettyPublisher.publish("admin.alerts",
                    new AdminEvent("review_negative", dto.getProductId(), review.getId(),
                            review.getContent(), user.getUsername()));
        }

        return review.getId();
    }
    
    @Transactional(readOnly = true)
    public List<ReviewResponseDTO> listByProduct(Long productId) {
        return productReviewRepository
                .findByProduct_ProductIdOrderByIdDesc(productId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    // 필요하면 컨트롤러와 이름을 맞추기 위한 위임 메서드(선택)
    public List<ReviewResponseDTO> list(Long productId) {
        return listByProduct(productId);
    }

    private ReviewResponseDTO toDto(ProductReview r) {
        return ReviewResponseDTO.builder()
                .id(r.getId())
                .productId(r.getProduct().getProductId()) // ✅ Long 그대로
                .content(r.getContent())
                .rating(r.getRating())
                .authorName(r.getUser() != null ? r.getUser().getName() : "익명")
                .createdAt(r.getCreatedAt())
                .build();
    }


    // === 헬퍼 & 이벤트 DTO ===
    private static String maskName(String name) {
        if (name == null || name.isBlank()) return "고객";
        // 한글 이름: 첫 글자 + ** (예: 박**)
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
