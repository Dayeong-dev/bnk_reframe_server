package com.example.reframe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.reframe.dto.DepositProductDTO;
import com.example.reframe.service.DepositProductService;
import com.example.reframe.service.DepositProductServiceImpl;
import com.example.reframe.session.RecentViewManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/deposit")
public class DepositProductController {
    private final DepositProductService depositProductService;
    private final DepositProductServiceImpl depositProductServiceImpl;
    private final RecentViewManager recentViewManager;

    /**
     * 예적금 메인 (추천상품, 테마별 추천 상품)
     */
    @GetMapping("/main")
    public String depositMain(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "theme", required = false) String theme,
            Model model) {

        // ✅ 기본 카테고리 설정
        if (category == null || category.isEmpty()) {
            category = "적금";
        }

        // ✅ 카테고리 기준 추천상품 조회 (상태: S)
        List<DepositProductDTO> products = depositProductService.getAllProducts("S", category);
        products.sort((a, b) -> Long.compare(b.getViewCount(), a.getViewCount())); // 조회수 기준 정렬

        // ✅ 하이라이트 및 나머지 상품 분리
        DepositProductDTO highlightProduct = products.isEmpty() ? null : products.get(0);
        List<DepositProductDTO> otherProducts = products.size() > 1
                ? products.subList(1, Math.min(products.size(), 5))
                : List.of();

        // ✅ 모델에 담기
        model.addAttribute("highlightProduct", highlightProduct);
        model.addAttribute("otherProducts", otherProducts);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedTheme", theme);

        // ✅ 테마별 추천상품
        model.addAttribute("workerProducts", depositProductService.getThemeRecommended("직장인").stream().limit(3).toList());
        model.addAttribute("housewifeProducts", depositProductService.getThemeRecommended("주부").stream().limit(3).toList());
        model.addAttribute("studentProducts", depositProductService.getThemeRecommended("학생").stream().limit(3).toList());

        return "user/deposit_main";
    }

    @GetMapping("/category")
    @ResponseBody
    public Map<String, Object> getProductsByCategory(
            @RequestParam("category") String category) {

        // 상태 S(판매중) + 카테고리 조건으로 조회
        List<DepositProductDTO> products = depositProductService.getAllProducts("S", category);
        products.sort((a, b) -> Long.compare(b.getViewCount(), a.getViewCount())); // 조회수 순 정렬

        // 하이라이트 + 추천 상품 분리
        DepositProductDTO highlight = products.isEmpty() ? null : products.get(0);
        List<DepositProductDTO> others = products.size() > 1
                ? products.subList(1, Math.min(products.size(), 5))
                : List.of();

        // JSON 형식으로 반환
        Map<String, Object> result = new HashMap<>();
        result.put("highlightProduct", highlight);
        result.put("otherProducts", others);
        return result;
    }


    /**
     * 예적금 상품 목록 (페이징, 정렬, 검색)
     */
    @GetMapping("/list")
    public String depositList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "sort", required = false, defaultValue = "recommend") String sort,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            Model model) {

        Page<DepositProductDTO> productsPage = depositProductService.getPagedProducts("S", null, keyword, sort, page);
        // "S" = 판매중, category = null (전체)

        model.addAttribute("products", productsPage.getContent());
        model.addAttribute("page", productsPage);
        model.addAttribute("products", productsPage.getContent()); // 현재 페이지의 상품 리스트
        model.addAttribute("page", productsPage);                  // 페이지네이션 정보
        model.addAttribute("keyword", keyword);
        model.addAttribute("sort", sort);

        return "user/deposit_list";
    }

    /**
     * 예적금 상품 상세 (상세 + 모달 데이터)
     */

    @GetMapping("/detail/{id}")
    public String depositDetail(@PathVariable("id") Long productId, Model model) { 
		try {
			// 상품 상세 조회 (+ 조회수 증가)
			DepositProductDTO product = depositProductService.getProductDetail(productId);
			
			// DETAIL/모달 정보
	        model.addAttribute("product", product);
	        model.addAttribute("detailForPage", product.getDetail());
	        model.addAttribute("modalDetailForModal", product.getModalDetail());

	        // 금리 정보 추가
	        model.addAttribute("modalRateTable", product.getModalRate());
//	        String rateHtml = interestRateService.getRateHtmlByProductId(productId);
//	        model.addAttribute("modalRateTable", rateHtml);

	        // 최근 본 상품 기록
	        recentViewManager.setProduct("deposit", product.getProductId(), product.getName());

	        return "user/deposit_detail";
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return "redirect:/";
    }

    

    
    @GetMapping("/autocomplete")
    @ResponseBody
    public List<String> getAutocomplete(@RequestParam("keyword") String keyword) {
        return depositProductService.findSuggestions(keyword); // 상품명 유사 매칭 리스트 반환
    }

    

    // 조회수 기준으로 Top 5 예적금 추천 : 메인 페이지
    @GetMapping("/recommend/list")
    public @ResponseBody ResponseEntity<List<DepositProductDTO>> getRecommendDeposits() {
    	List<DepositProductDTO> depositList = depositProductServiceImpl.getTopFiveByViewCount();
    	
    	if(depositList == null) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    	}
    	
    	return ResponseEntity.status(HttpStatus.OK).body(depositList);
    }
}