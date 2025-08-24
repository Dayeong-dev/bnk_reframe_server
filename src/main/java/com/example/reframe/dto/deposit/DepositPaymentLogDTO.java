package com.example.reframe.dto.deposit;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.reframe.dto.enroll.ProductApplicationDTO;
import com.example.reframe.entity.deposit.PaymentStatus;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class DepositPaymentLogDTO {
    private Long id;
    private ProductApplicationDTO application;
    private LocalDateTime paidAt;
    private Long amount;
    private Integer round;
    private PaymentStatus status;
    
    private Long walkStepsTotal;
    private Long walkThresholdSteps;
    private BigDecimal walkBonusApplied;
    private String walkConfirmedYn = "N";
    private LocalDateTime walkConfirmedAt;
    private LocalDateTime walkLastSyncDate;
    private Long walkLastSyncSteps = 0L;
}