package com.example.reframe.service;

import com.example.reframe.dto.InterestRateDTO;

import java.util.List;

public interface InterestRateService {
   
    String getRateHtmlByProductId(Long productId); // 🔹 이 메소드 추가
}
