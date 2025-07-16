package com.example.reframe.service;

import com.example.reframe.dto.InterestRateDTO;

import java.util.List;

public interface InterestRateService {
    List<InterestRateDTO> getRatesByProductId(Long productId);
}
