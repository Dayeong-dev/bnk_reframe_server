package com.example.reframe.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.reframe.dto.DailyVisitDTO;
import com.example.reframe.dto.ProductViewDTO;
import com.example.reframe.service.AccessLogService;
import com.example.reframe.service.ProductService;
import com.example.reframe.service.ReviewService;
import com.example.reframe.service.TestResultService;

@Controller
@RequestMapping("/admin/chart")
public class AdminChartController {

	@Autowired
	private AccessLogService accessLogService;
	@Autowired
	private ProductService ProductService;
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private TestResultService testResultService;
	
	
	// 차트
	@GetMapping("/chart-main")
	public String chartMain(Model model){

		// 방문자 수 
		List<DailyVisitDTO> visitData = accessLogService.getDailyStats();
		List<String> visitLabels = new ArrayList<>();
        List<Long> visitCounts = new ArrayList<>();

		for (DailyVisitDTO dto : visitData) {
			visitLabels.add(dto.getDate());
			visitCounts.add(dto.getCount());
		}
		model.addAttribute("visitLabels", visitLabels);
		model.addAttribute("visitCounts", visitCounts);
	    
		 // 상품 조회 수
		List<ProductViewDTO> products = ProductService.getTopViewedProducts();
	    List<String> labels = new ArrayList<>();
	    List<Long> counts = new ArrayList<>();

	    for (ProductViewDTO p : products) {
	        labels.add(p.getName());
	        counts.add(p.getViewCount());
	    }
	    model.addAttribute("labels", labels);
	    model.addAttribute("counts", counts);

	    // 리뷰 긍정, 부정 
	    Map<String, Long> sentimentStats = reviewService.getReviewSentimentStats();
        model.addAttribute("sentimentLabels", sentimentStats.keySet());
        model.addAttribute("sentimentCounts", sentimentStats.values());
        
        // MBTI 테스트 결과 통계 바인딩
        Map<String, Long> testResultStats = testResultService.getTestResultStats();
        model.addAttribute("testResultLabels", testResultStats.keySet());
        model.addAttribute("testResultCounts", testResultStats.values());
		
		return "admin/chart/chart-main";
	}
	
	
	
		
}
