package com.example.reframe.controller;

import com.example.reframe.dto.InterestRateDTO;
import com.example.reframe.service.InterestRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class InterestRateController {

    private final InterestRateService interestRateService;

    @GetMapping("/deposit/detail/{productId}")
    public String getProductDetail(@PathVariable("productId") Long productId, Model model) {
        List<InterestRateDTO> rateList = interestRateService.getRatesByProductId(productId);
        model.addAttribute("rateList", rateList);
        model.addAttribute("productId", productId); // 상품정보 조회할 경우 필요
        return "user/deposit_detail"; // 기존 템플릿 유지
    }
}
