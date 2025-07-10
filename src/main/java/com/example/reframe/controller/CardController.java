package com.example.reframe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.reframe.dto.CardDto;
import com.example.reframe.service.CardService;

@Controller
@RequestMapping("/card")
public class CardController {

	@Autowired
	private CardService cardService;

	// 메인 페이지
	@GetMapping("/main")
	public String cardMain(Model model) {
		List<CardDto> topCards = cardService.getTop6Cards();
		model.addAttribute("topCards", topCards);
		return "user/card_main";
	}

	// 상세 페이지
	@GetMapping("/detail/{cardId}")
	public String cardDetail(@PathVariable("cardId") Long cardId, Model model) {
		CardDto cardDto = cardService.getCardDetail(cardId);
		model.addAttribute("card", cardDto);
		return "user/card_detail";
	}
}
