package com.example.reframe.service;

import org.springframework.stereotype.Service;

import com.example.reframe.entity.InterestRate;
import com.example.reframe.repository.InterestRateRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InterestRateServiceImpl implements InterestRateService {

    private final InterestRateRepository interestRateRepository;

   
    
    @Override
    public String getRateHtmlByProductId(Long productId) {
        return interestRateRepository.findFirstByProductId(productId)
        		.map(InterestRate::getHtmlTable)
                .orElse("<p style='text-align:center;'>금리 정보가 없습니다.</p>");
    }






}
