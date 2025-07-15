package com.example.reframe.controller;

import java.util.List;
import com.example.reframe.session.RecentViewManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.reframe.dto.CardDto;
import com.example.reframe.service.CardService;

@Controller
@RequestMapping("/card")
public class CardController {

	@Autowired
    private RecentViewManager recentViewManager;

	@Autowired
	private CardService cardService;

    CardController(RecentViewManager recentViewManager) {
        this.recentViewManager = recentViewManager;
    }

	// 메인 페이지
	@GetMapping("/main")
	public String cardMain(Model model) {
		List<CardDto> topCards = cardService.getTop6Cards(); // 기준: 조회수TOP6

		model.addAttribute("topCards", topCards);
		return "user/card_main";
	}

	// 상세 페이지
	@GetMapping("/detail/{cardId}")
	public String cardDetail(@PathVariable("cardId") Long cardId, Model model) {
		cardService.incrementViewCount(cardId); // 카드 상세페이지 클릭할 때마다 조회수 +1
		CardDto cardDto = cardService.getCardDetail(cardId);
		model.addAttribute("card", cardDto);

		// 최근 본 상품 등록
		recentViewManager.setProduct("card", cardDto.getCardId(), cardDto.getName());
		
		return "user/card_detail";
	}

	// 목록 페이지
	@GetMapping("/list")
	public String cardList(@RequestParam(name = "categoryMajor", defaultValue = "전체") String categoryMajor, // 카드 대분류
			@RequestParam(name = "subcategory", defaultValue = "전체") String subcategory, // 카드 소분류
			@RequestParam(name = "keyword", required = false) String keyword, // 검색 키워드
			Pageable pageable, Model model) {

		Page<CardDto> cards = cardService.getCards(categoryMajor, subcategory, keyword, pageable);
		List<String> allSubcategories = cardService.getAllSubcategories();

		model.addAttribute("cards", cards);
		model.addAttribute("categoryMajor", categoryMajor);
		model.addAttribute("subcategory", subcategory);
		model.addAttribute("keyword", keyword);
		model.addAttribute("allSubcategories", allSubcategories);

		return "user/card_list";
	}

	@GetMapping("/compare") // 카드 비교
	@ResponseBody
	public List<CardDto> compareCards(@RequestParam("ids") List<Long> ids) {
		return cardService.getCardsForCompare(ids);
	}

	// 카드 테스트 시작화면
	@GetMapping("/card_test")
	public String cardTestMain() {
		return "user/card_test";
	}

	// 결과 저장 + 추천카드 반환
	@PostMapping("/card_test_result")
	@ResponseBody
	public List<CardDto> processTestResult(@RequestParam String resultType) {
		cardService.saveOrUpdateTestResult(resultType);
		return cardService.getRecommendedCardsByResult(resultType);
	}
	
	
	// 조회수 기준으로 Top 5 카드 추천 : 메인 페이지
	@GetMapping("/recommend/list")
	public @ResponseBody ResponseEntity<List<CardDto>> getRecommendCards() {
    	List<CardDto> cardList = cardService.getTopFiveByViewCount();
    	
    	if(cardList == null) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    	}
    	
    	return ResponseEntity.status(HttpStatus.OK).body(cardList);
    }

}
