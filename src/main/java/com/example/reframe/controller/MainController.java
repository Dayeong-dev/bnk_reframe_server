package com.example.reframe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.reframe.dto.CardDto;
import com.example.reframe.dto.DepositProductDTO;
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
	public String searchProduct(@RequestParam("keyword") String keyword, Model model) {
		SearchResultResponse searchResult = mainService.searchByKeywords(keyword);
		
		List<DepositProductDTO> depositList = searchResult.getDeposits()
				.subList(0, Math.min(4, searchResult.getDeposits().size()));	// 4개만 출력
		List<CardDto> cardList = searchResult.getCards()
				.subList(0, Math.min(3, searchResult.getCards().size()));	// 4개만 출력
		
		model.addAttribute("depositList", depositList);
		model.addAttribute("cardList", cardList);
		model.addAttribute("keyword", keyword);
		
		return "user/search_list";
		
	}
}
