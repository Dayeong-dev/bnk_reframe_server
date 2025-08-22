package com.example.reframe.entity.deposit;

import java.time.LocalDateTime;

import com.example.reframe.entity.ProductApplication;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
    name = "deposit_payment_log",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_deposit_payment_log_app_round",
            columnNames = {"application_id", "round"}
        )
    }
)
@Getter
@Setter
@ToString(exclude = "application")
public class DepositPaymentLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                              // 납입 이력 ID

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "application_id", nullable = false)
    private ProductApplication application;       // 상품 가입(FK)

    @Column(name = "paid_at", nullable = false)
    private LocalDateTime paidAt;                 // 납입 일시

    @Column(nullable = false)
    private Long amount;                          // 납입 금액

    @Column(nullable = false)
    private Integer round;                        // 회차

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private PaymentStatus status;                 // PAID / UNPAID
}