// src/main/java/com/example/reframe/controller/admin/AdminGangController.java
package com.example.reframe.controller.admin;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @Autowired FaqRepository faqRepository;
    @Autowired QnaRepository qnaRepository;
    @Autowired ReviewRepository reviewRepository;
    @Autowired AdminAlertRepository adminAlertRepository;

    @GetMapping("/index2")
    public String index2(Model model) {
        List<Faq> faqlist = faqRepository.findAllByOrderByFaqIdAsc();
        model.addAttribute("faqlist", faqlist);
        return "admin/faqIndex";
    }

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

    // ✅ 관리자 QnA 목록 페이지
    @GetMapping("/index3")
    public String index3(Model model) {
        // 유저 조인으로 LAZY/지연로딩 문제 방지
        List<Qna> qnaEntities = qnaRepository.findAllWithUser();
        List<QnaDTO> qnalist = qnaEntities.stream()
                                          .map(QnaDTO::new) // username 포함됨
                                          .collect(Collectors.toList());
        model.addAttribute("qnalist", qnalist);
        return "admin/qnaIndex";
    }

    // ✅ 상세 JSON: username 포함해서 내려줌
    @ResponseBody
    @GetMapping("/qnaDetail/{qnaId}")
    public QnaDTO qnaDetail(@PathVariable("qnaId") Integer qnaId) {
        Qna qna = qnaRepository.findByIdWithUser(qnaId).orElse(null);
        return qna != null ? new QnaDTO(qna) : null;
    }

    // ✅ 답변 등록: 저장 후 DTO(username 포함) 반환
    @PostMapping("/submitAnswer")
    @ResponseBody
    public QnaDTO submitAnswer(@RequestBody Map<String, String> payload) {
        Integer qnaId = Integer.parseInt(payload.get("qnaId"));
        String answer = payload.get("answer");

        Qna qna = qnaRepository.findByIdWithUser(qnaId).orElse(null);
        if (qna != null) {
            qna.setAnswer(answer);
            qna.setStatus("답변완료");
            qnaRepository.save(qna);
            return new QnaDTO(qna);
        }
        return null;
    }

    @PostMapping("/faqUpdate")
    public ResponseEntity<String> updateFaq(@ModelAttribute Faq faq) {
        faqRepository.save(faq);
        return ResponseEntity.ok("수정 완료");
    }

    @DeleteMapping("/faqDelete/{faqId}")
    public ResponseEntity<String> deleteFaq(@PathVariable("faqId") Integer faqId) {
        faqRepository.deleteById(faqId);
        return ResponseEntity.ok("삭제 완료");
    }

    @GetMapping("/index4")
    public String index4(Model model) {
        List<Review> reviewlist = reviewRepository.findAll();

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
