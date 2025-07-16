package com.example.reframe.service;

import com.example.reframe.dto.InterestRateDTO;
import com.example.reframe.entity.InterestRate;
import com.example.reframe.repository.InterestRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InterestRateServiceImpl implements InterestRateService {

    private final InterestRateRepository interestRateRepository;

    @Override
    public List<InterestRateDTO> getRatesByProductId(Long productId) {
        List<InterestRate> rates = interestRateRepository.findByProductIdOrderByCategoryAscTypeAsc(productId);
        return rates.stream()
                .map(r -> InterestRateDTO.builder()
                        .id(r.getId())
                        .productId(r.getProductId())
                        .category(r.getCategory())
                        .type(r.getType())
                        .rate(r.getRate())
                        .annualReturn(r.getAnnualReturn())
                        .note(r.getNote())
                        .build())
                .collect(Collectors.toList());
    }
}
