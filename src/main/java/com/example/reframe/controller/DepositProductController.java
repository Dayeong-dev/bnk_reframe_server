package com.example.reframe.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.reframe.dto.DepositProductDTO;
import com.example.reframe.service.DepositProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/deposit")
public class DepositProductController {

    private final DepositProductService depositProductService;

    @GetMapping("/main")
    public String depositMain(@RequestParam(value = "purpose", required = false) String purpose,
                              @RequestParam(value = "theme", required = false) String theme,
                              Model model) {
        if (purpose == null || purpose.isEmpty()) {
            purpose = "목돈 만들기";
        }

        List<DepositProductDTO> products = depositProductService.getProductsByPurpose(purpose);
        products.sort((a, b) -> Long.compare(b.getViewCount(), a.getViewCount()));

        DepositProductDTO highlightProduct = products.isEmpty() ? null : products.get(0);
        List<DepositProductDTO> otherProducts = products.size() > 1 ? products.subList(1, Math.min(products.size(), 5)) : List.of();

        model.addAttribute("highlightProduct", highlightProduct);
        model.addAttribute("otherProducts", otherProducts);
        model.addAttribute("selectedPurpose", purpose);

        // ✅ 테마별 데이터 내려주기
        model.addAttribute("workerProducts", depositProductService.getProductsByPurpose("직장인").stream().limit(3).toList());
        model.addAttribute("housewifeProducts", depositProductService.getProductsByPurpose("주부").stream().limit(3).toList());
        model.addAttribute("studentProducts", depositProductService.getProductsByPurpose("학생").stream().limit(3).toList());

        return "user/deposit_main";
    }




    @GetMapping("/detail/{id}")
    public String depositDetail(@PathVariable("id") Long productId, Model model) {
        DepositProductDTO product = depositProductService.getProductDetail(productId);
        model.addAttribute("product", product);
        return "user/deposit_detail";
    }
    
    @GetMapping("/list")
    public String depositList(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<DepositProductDTO> products;
        if (keyword != null && !keyword.isBlank()) {
            products = depositProductService.searchProducts(keyword);
        } else {
            products = depositProductService.getAllProducts("S", null); // 판매중 전체
        }
        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        return "user/deposit_list";
    }

   

}
