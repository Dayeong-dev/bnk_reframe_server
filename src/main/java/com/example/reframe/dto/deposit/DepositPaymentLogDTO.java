package com.example.reframe.dto.deposit;

import java.time.LocalDateTime;

import com.example.reframe.dto.enroll.ProductApplicationDTO;
import com.example.reframe.entity.deposit.PaymentStatus;

import lombok.Data;

@Data
public class DepositPaymentLogDTO {
    private Long id;
    private ProductApplicationDTO application;
    private LocalDateTime paidAt;
    private Long amount;
    private Integer round;
    private PaymentStatus status;
}