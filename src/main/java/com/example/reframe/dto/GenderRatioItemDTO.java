package com.example.reframe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenderRatioItemDTO {
	 // 차트에 표시할 라벨(남성/여성/미상 등)
    private String label;
    // 라벨별 가입자 수(중복 없는 사용자 수)
    private long count;
    // 전체 대비 비율(%), 소수점 1자리
    private double percentage;
}
