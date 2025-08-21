package com.example.reframe.controller.api.deposit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.reframe.dto.DepositProductDTO;
import com.example.reframe.dto.deposit.ProductInputFormatDTO;
import com.example.reframe.service.DepositProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mobile/deposit")
public class DepositController {
    private final DepositProductService depositProductService;
	
    @GetMapping("/detail/{id}")
    @ResponseBody
    public ResponseEntity<DepositProductDTO> getProductDetailJson(@PathVariable("id") Long productId) throws JsonMappingException, JsonProcessingException {
        DepositProductDTO product = depositProductService.getProductDetail2(productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<List<DepositProductDTO>> getAllProducts() {
        List<DepositProductDTO> productList = depositProductService.getAllProducts("S", null); // 판매중 상품 전체
        return ResponseEntity.ok(productList);
    }
    
    @GetMapping("/preview")
    @ResponseBody
    public Map<String, List<DepositProductDTO>> getPreviewByCategory() {
        Map<String, List<DepositProductDTO>> previewMap = new HashMap<>();

        List<String> categories = List.of("예금", "적금", "입출금자유");

        for (String category : categories) {
            List<DepositProductDTO> list = depositProductService.getAllProducts("S", category);
            previewMap.put(category, list.stream().limit(3).toList());
        }

        return previewMap;
    }

 // DepositProductController.java
    @GetMapping("/category")
    @ResponseBody
    public List<DepositProductDTO> getProductsByCategoryAPI(@RequestParam("category") String category) {
        return depositProductService.getAllProducts("S", category);
    }
    
    @GetMapping("/format/{productId}")
    @ResponseBody
    public ProductInputFormatDTO getProductInputFormat(@PathVariable("productId") Long productId) {
    	return depositProductService.getProductInputFormat(productId);
    }
}
