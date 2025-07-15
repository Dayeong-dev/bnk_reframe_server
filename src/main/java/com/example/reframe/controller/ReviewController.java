package com.example.reframe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reframe.auth.CustomUserDetails;
import com.example.reframe.dto.ReviewDTO;
import com.example.reframe.entity.AdminAlert;
import com.example.reframe.entity.Review;
import com.example.reframe.entity.User;
import com.example.reframe.repository.AdminAlertRepository;
import com.example.reframe.repository.ReviewRepository;
import com.example.reframe.repository.UserRepository;
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
	UserRepository userRepository;
	
	@Autowired
	AdminAlertRepository adminAlertRepository;

	@PostMapping("/submit")
	public ResponseEntity<String> submitReview(@RequestBody ReviewDTO dto, 
	                                           @AuthenticationPrincipal CustomUserDetails user) {
	    // 유저 정보 확인 및 리뷰 저장
	    User user1 = userRepository.findById(user.getUsername())
	            .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다"));

	    Review review = Review.builder()
	            .user(user1)
	            .content(dto.getContent())
	            .build();
	    reviewRepository.save(review); // 먼저 저장

	    // 비동기로 AI 분석 처리 (새 스레드)
	    new Thread(() -> {
	        try {
	            String prompt = "다음 리뷰가 부정적인 리뷰인지 감지해줘. 부정이면 true, 아니면 false로만 답해줘: " + dto.getContent();
	            String result = openAIService.askChatGPT(prompt);

	            boolean isNegative = result.trim().toLowerCase().contains("true");
	            review.setNegative(isNegative);
	            reviewRepository.save(review);

	            if (isNegative) {
	                AdminAlert alert = adminAlertRepository.findById(1).orElse(new AdminAlert());
	                alert.setNegativeReviewCount(alert.getNegativeReviewCount() + 1);
	                adminAlertRepository.save(alert);
	            }
	        } catch (Exception e) {
	            System.err.println("AI 분석 실패: " + e.getMessage());
	        }
	    }).start();

	    return ResponseEntity.ok("리뷰가 등록되었습니다.");
	}

}
