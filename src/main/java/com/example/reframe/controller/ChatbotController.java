package com.example.reframe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.reframe.service.AccessLogService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ChatbotController {
	@Autowired
	private AccessLogService accessLogService;

	// 방문자 수 데이터 수집
	@GetMapping("/")
	public String index(HttpServletRequest request, Model model) {
		// ip 저장
		String ip = request.getRemoteAddr();
		accessLogService.logVisitorOnce(ip);
		// ip 방문자수 확인
//        	long count = accessLogService.getTodayVistorCount();
//        	model.addAttribute("todayCount", count);
		return "user/index"; // 메인 페이지
	}

	@GetMapping("/chat")
	public String chat() {
		return "user/chat"; // 챗봇 페이지
	}

    @GetMapping("/chatbot")
    public String chatbot() {
        return "user/chatbot"; // 챗봇 페이지
    }
    
    @GetMapping("/game")
    public String game() {
        return "user/game"; // 게임 페이지
    }
    
    @GetMapping("/review")
    public String review() {
        return "user/review"; // 리뷰 페이지
    }
}