package com.example.reframe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.reframe.dto.SearchResultResponse;
import com.example.reframe.service.AccessLogService;
import com.example.reframe.service.MainService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MainController {
	@Autowired
	private AccessLogService accessLogService;
	
	@Autowired
	private MainService mainService;

	/* 방문자 수 데이터 수집 */
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
	
	/* 메인 검색 */
	@GetMapping("/search")
	public String searchProduct(@RequestParam("keywords") String keywords, Model model) {
		SearchResultResponse searchResult = mainService.searchByKeywords(keywords);
		
		model.addAttribute("depositList", searchResult.getDeposits());
		model.addAttribute("cardList", searchResult.getCards());
		
		return "user/search_list";
		
	}
}
