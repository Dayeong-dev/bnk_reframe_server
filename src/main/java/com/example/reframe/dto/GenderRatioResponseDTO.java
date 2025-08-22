package com.example.reframe.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenderRatioResponseDTO {
    // Chart.js 등에서 바로 쓰기 쉬운 구조
    private List<String> labels;       // ["남성","여성","미상"]
    private List<Long> data;           // [120, 95, 7]
    private List<Double> percentages;  // [53.3, 42.2, 3.5]
    private long total;                // 총 가입자 수(중복 없는 사용자 수)
}