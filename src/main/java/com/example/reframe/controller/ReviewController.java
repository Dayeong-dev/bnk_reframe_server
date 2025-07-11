package com.example.reframe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reframe.dto.ReviewDTO;
import com.example.reframe.entity.Review;
import com.example.reframe.entity.Userr;
import com.example.reframe.repository.ReviewRepository;
import com.example.reframe.repository.UserrRepository;
import com.example.reframe.service.OpenAIService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	OpenAIService openAIService;
	
	@Autowired
	UserrRepository userrRepository;
	
//    private final ReviewRepository reviewRepository;
//    private final OpenAIService openAIService;

//    public ReviewController(ReviewRepository reviewRepository, OpenAIService openAIService) {
//        this.reviewRepository = reviewRepository;
//        this.openAIService = openAIService;
//    }

    @PostMapping("/submit")
    public ResponseEntity<String> submitReview(@RequestBody ReviewDTO dto, HttpServletRequest request) {
        // 부정 리뷰 판별
        String prompt = "다음 리뷰가 부정적인 리뷰인지 감지해줘. 부정이면 true, 아니면 false로만 답해줘: " + dto.getContent();
        String result = openAIService.askChatGPT(prompt);
        System.out.println(prompt);
        System.out.println(result);

        boolean isNegative = result.trim().toLowerCase().contains("true");

        dto.setUserId("user01");
        Userr user = userrRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다: " + dto.getUserId()));
        Review review = Review.builder()
                .user(user) // 로그인 유저 주입 필요
                .content(dto.getContent())
                .negative(isNegative)
                .build();
        reviewRepository.save(review);

        if (isNegative) {
            // 관리자 알림 시스템 연동 지점
            System.out.println("⚠ 부정 리뷰 감지됨! 고객센터에 알림 발송 필요.");
        }

        return ResponseEntity.ok("리뷰가 등록되었습니다.");
    }
}
