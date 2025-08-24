package com.example.reframe.entity.deposit;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    
    // ------------------ [추가: 걷기 우대 스냅샷/동기화] ------------------

    @Column(name = "walk_steps_total", nullable = false)
    private Long walkStepsTotal = 0L;             // 해당 월 누적 걸음 (DB: DEFAULT 0 NOT NULL)

    @Column(name = "walk_threshold_steps")
    private Long walkThresholdSteps;              // 임계치(100,000 or 50,000)

    @Column(
        name = "walk_bonus_applied",
        nullable = false,
        precision = 5,  // NUMBER(5,2)
        scale = 2
    )
    private BigDecimal walkBonusApplied = BigDecimal.ZERO; // 0.00 or 0.83

    @Column(name = "walk_confirmed_yn", nullable = false, length = 1)
    private String walkConfirmedYn = "N";         // 'Y' / 'N'  (DB CHECK 제약)

    @Column(name = "walk_confirmed_at")
    private LocalDateTime walkConfirmedAt;        // 확정 시각

    @Column(name = "walk_last_sync_date")
    private LocalDate walkLastSyncDate;           // 마지막 처리 '그 날' (DATE → LocalDate 매핑)

    @Column(name = "walk_last_sync_steps", nullable = false)
    private Long walkLastSyncSteps = 0L;          // 마지막 처리 시 그 날의 누적값 (DB: DEFAULT 0 NOT NULL)
}