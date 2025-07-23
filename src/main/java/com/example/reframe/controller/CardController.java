package com.example.reframe.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.reframe.session.RecentViewManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.reframe.dto.CardDto;
import com.example.reframe.service.CardService;

import jakarta.servlet.http.HttpSession;

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
	    List<CardDto> allCards = cardService.findAllCards(); // 전체 카드
		List<CardDto> topCards = cardService.getTop6Cards(); // 기준: 조회수TOP6
		
		model.addAttribute("allCards", allCards);
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
			 Model model) {
	    
		List<CardDto> cards = cardService.getCards(categoryMajor, subcategory, keyword);
		List<String> allSubcategories = cardService.getAllSubcategories();

		model.addAttribute("cards", cards);
		model.addAttribute("categoryMajor", categoryMajor);
		model.addAttribute("subcategory", subcategory);
		model.addAttribute("keyword", keyword);
		model.addAttribute("allSubcategories", allSubcategories);

		return "user/card_list";
	}
	
	
	//카드 비교함
	//카드 추가
	@PostMapping("/compare/add")
	@ResponseBody
	public ResponseEntity<?> addToCompare(@RequestParam("cardId") Long cardId, HttpSession session) {
	    List<Long> compareList = (List<Long>) session.getAttribute("compareList");
	    if (compareList == null) {
	        compareList = new ArrayList<>();
	    }
	    if (!compareList.contains(cardId)) {
	        compareList.add(cardId);
	    }
	    session.setAttribute("compareList", compareList);
	    return ResponseEntity.ok().build();
	}

	//카드 삭제
	@PostMapping("/compare/remove")
	@ResponseBody
	public ResponseEntity<?> removeFromCompare(@RequestBody Map<String, Long> payload, HttpSession session) {
	    Long cardId = payload.get("cardId");
	    if (cardId == null) {
	        return ResponseEntity.badRequest().build();
	    }

	    List<Long> compareList = (List<Long>) session.getAttribute("compareList");
	    if (compareList != null) {
	        compareList.remove(cardId);
	        session.setAttribute("compareList", compareList);
	    }
	    return ResponseEntity.ok().build();
	}
	
	//카드 비교
	@GetMapping("/compare/list")
	@ResponseBody
	public List<CardDto> getCompareList(HttpSession session) {
	    List<Long> compareList = (List<Long>) session.getAttribute("compareList");
	    if (compareList == null) {
	        return List.of();
	    }
	    return cardService.getCardsForCompare(compareList);
	}
	
	

	// 카드 테스트 시작화면
	@GetMapping("/card_test")
	public String cardTestMain() {
		return "user/card_test";
	}

	// 결과 저장 + 추천카드 반환
	@PostMapping("/card_test_result")
	@ResponseBody
	public void  processTestResult(@RequestParam("resultId") String resultType) {
		cardService.saveOrUpdateTestResult(resultType);
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
