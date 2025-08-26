package com.example.reframe.dto.account;

import java.math.BigDecimal;

public record GlobalAvgResponse(
    BigDecimal avgUserTotal,	// 전체 사용자 1인당 평균 총자산
    long usersCount				// 표본 사용자 수
) {}
