package com.example.reframe.controller.admin;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.reframe.dto.QnaDTO;
import com.example.reframe.entity.AdminAlert;
import com.example.reframe.entity.Faq;
import com.example.reframe.entity.Qna;
import com.example.reframe.entity.Review;
import com.example.reframe.repository.AdminAlertRepository;
import com.example.reframe.repository.FaqRepository;
import com.example.reframe.repository.QnaRepository;
import com.example.reframe.repository.ReviewRepository;

@Controller
@RequestMapping("/admin")
public class AdminGangController {
	
	@Autowired
	FaqRepository faqRepository;
	
	@Autowired
	QnaRepository qnaRepository;
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	AdminAlertRepository adminAlertRepository;
	
	@GetMapping("/index2")
	public String index2(Model model) {
		List<Faq> faqlist = faqRepository.findAllByOrderByFaqIdAsc();
		System.out.println(faqlist);
		model.addAttribute("faqlist", faqlist);
		return "admin/faqIndex";
	}
	
	//관리자용
	@ResponseBody
	@PostMapping("/faqRegist")
	public String registBoard(Faq faq) {
		faqRepository.save(faq);
		return "등록 성공";
	}
	
	@GetMapping("/detail/{faqId}")
	@ResponseBody
	public Faq faqDetail(@PathVariable("faqId") Integer faqId) {
	    return faqRepository.findById(faqId).orElse(null);
	}

	@GetMapping("/index3")
	public String index3(Model model) {
	    List<Qna> qnaEntities = qnaRepository.findAll();
	    
	    // Entity -> DTO 변환
	    List<QnaDTO> qnalist = qnaEntities.stream()
	                                      .map(QnaDTO::new)
	                                      .collect(Collectors.toList());

	    model.addAttribute("qnalist", qnalist);
	    return "admin/qnaIndex";
	}

	
	@ResponseBody
	@GetMapping("/qnaDetail/{qnaId}")
	public QnaDTO qnaDetail(@PathVariable("qnaId") Integer qnaId) {
	    Qna qna = qnaRepository.findById(qnaId).orElse(null);
	    return qna != null ? new QnaDTO(qna) : null;
	}
	
	@PostMapping("/submitAnswer")
	@ResponseBody
	public QnaDTO submitAnswer(@RequestBody Map<String, String> payload) {
	    Integer qnaId = Integer.parseInt(payload.get("qnaId"));
	    String answer = payload.get("answer");

	    Qna qna = qnaRepository.findById(qnaId).orElse(null);
	    if (qna != null) {
	        qna.setAnswer(answer);
	        qna.setStatus("답변완료");
	        qnaRepository.save(qna);
	        return new QnaDTO(qna); // ✅ JSON으로 DTO 반환
	    }
	    return null;
	}
	
	// 등록 or 수정
	@PostMapping("/faqUpdate")
	public ResponseEntity<String> updateFaq(@ModelAttribute Faq faq) {
	    faqRepository.save(faq); // faqId 존재하면 update
	    return ResponseEntity.ok("수정 완료");
	}

	// 삭제
	@DeleteMapping("/faqDelete/{faqId}")
	public ResponseEntity<String> deleteFaq(@PathVariable("faqId") Integer faqId) {
	    faqRepository.deleteById(faqId);
	    return ResponseEntity.ok("삭제 완료");
	}
	
	@GetMapping("/index4")
	public String index4(Model model) {
		List<Review> reviewlist = reviewRepository.findAll();
		
	    // 🚨 알림 카운트 초기화
	    AdminAlert alert = adminAlertRepository.findById(1).orElse(null);
	    if (alert != null) {
	        alert.setNegativeReviewCount(0);
	        adminAlertRepository.save(alert);
	    }
		
		model.addAttribute("reviewlist", reviewlist);
		return "admin/reviewIndex";
	}

	@GetMapping("/alertCount")
	@ResponseBody
	public int getAlertCount() {
	    return adminAlertRepository.findById(1)
	            .map(AdminAlert::getNegativeReviewCount)
	            .orElse(0);
	}



}
