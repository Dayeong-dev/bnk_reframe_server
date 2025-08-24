// src/main/java/com/example/reframe/controller/admin/AdminGangController.java
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.reframe.dto.ProductReviewAdminDTO;
import com.example.reframe.dto.QnaDTO;
import com.example.reframe.entity.AdminAlert;
import com.example.reframe.entity.DepositProduct;        // ▼ 신규: 상품
import com.example.reframe.entity.Faq;
import com.example.reframe.entity.ProductReview;         // ▼ 신규: 상품 리뷰
import com.example.reframe.entity.Qna;
import com.example.reframe.entity.Review;                 // (기존 사이트 리뷰 엔티티)
import com.example.reframe.repository.AdminAlertRepository;
import com.example.reframe.repository.DepositProductRepository;   // ▼
import com.example.reframe.repository.FaqRepository;
import com.example.reframe.repository.ProductReviewRepository;    // ▼
import com.example.reframe.repository.QnaRepository;
import com.example.reframe.repository.ReviewRepository;

@Controller
@RequestMapping("/admin")
public class AdminGangController {

    @Autowired FaqRepository faqRepository;
    @Autowired QnaRepository qnaRepository;
    @Autowired ReviewRepository reviewRepository;                // (기존: 사이트 리뷰)
    @Autowired AdminAlertRepository adminAlertRepository;

    // ▼ 신규 주입
    @Autowired DepositProductRepository depositProductRepository;
    @Autowired ProductReviewRepository productReviewRepository;

    // --- FAQ ---
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

    // --- QNA ---
    @GetMapping("/index3")
    public String index3(Model model) {
        List<Qna> qnaEntities = qnaRepository.findAllWithUser();
        List<QnaDTO> qnalist = qnaEntities.stream()
                                          .map(QnaDTO::new)
                                          .collect(Collectors.toList());
        model.addAttribute("qnalist", qnalist);
        return "admin/qnaIndex";
    }

    @ResponseBody
    @GetMapping("/qnaDetail/{qnaId}")
    public QnaDTO qnaDetail(@PathVariable("qnaId") Integer qnaId) {
        Qna qna = qnaRepository.findByIdWithUser(qnaId).orElse(null);
        return qna != null ? new QnaDTO(qna) : null;
    }

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

    // --- 리뷰 인덱스(사이트 리뷰 + 상품리뷰 셀렉터 제공) ---
    @GetMapping("/index4")
    public String index4(Model model) {
        // (A) 기존 사이트 리뷰 목록
        List<Review> reviewlist = reviewRepository.findAll();

        // 알림 카운트 리셋
        AdminAlert alert = adminAlertRepository.findById(1).orElse(null);
        if (alert != null) {
            alert.setNegativeReviewCount(0);
            adminAlertRepository.save(alert);
        }

        // (B) 상품 드롭다운용 목록
        List<DepositProduct> products = depositProductRepository.findAll();

        model.addAttribute("reviewlist", reviewlist);
        model.addAttribute("products", products);
        return "admin/reviewIndex";
    }

    // --- 상품 리뷰 JSON (상품 선택 시 비동기 로드) ---
    @GetMapping("/product-reviews")
    @ResponseBody
    public List<ProductReviewAdminDTO> getProductReviews(@RequestParam("productId") Long productId) {
        List<ProductReview> list = productReviewRepository.findAllByProductIdWithUser(productId);

        return list.stream().map(r -> new ProductReviewAdminDTO(
            r.getId(),
            r.getProduct() != null ? r.getProduct().getProductId() : null,
            (r.getProduct() != null && r.getProduct().getName() != null) ? r.getProduct().getName() : "",
            (r.getUser() != null && r.getUser().getUsername() != null) ? r.getUser().getUsername() : "익명",
            r.getRating(), // Integer는 null 가능
            r.getContent() != null ? r.getContent() : "",
            r.isNegative(),
            r.getCreatedAt() != null ? r.getCreatedAt().getTime() : null
        )).toList();
    }

    // --- 관리자 알림 카운트 ---
    @GetMapping("/alertCount")
    @ResponseBody
    public int getAlertCount() {
        return adminAlertRepository.findById(1)
                .map(AdminAlert::getNegativeReviewCount)
                .orElse(0);
    }
}
