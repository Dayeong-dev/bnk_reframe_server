package com.example.reframe.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.reframe.entity.account.Account;
import com.example.reframe.entity.auth.User;

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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "product_application")
@Data
public class ProductApplication {

	public enum ApplicationStatus {
		STARTED, CLOSED, CANCELED
	}

	@Id
	@SequenceGenerator(
	    name = "pa_seq_gen",
	    sequenceName = "PRODUCT_APPLICATION_ID_SEQ",
	    allocationSize = 1
	)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pa_seq_gen")
    private Long id; // 상품 가입 ID

    // 회원 고유 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 상품 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private DepositProduct product;

    // 개설된 상품 계좌 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_account_id", nullable = false)
    private Account productAccount;

    // 납입 출금 계좌 ID (FK, nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account_id", nullable = true)
    private Account fromAccount;

    // 만기 원리금 입금 계좌 ID (FK, nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maturity_account_id", nullable = true)
    private Account maturityAccount;

    // 가입 상태 (STARTED, CLOSED, CANCELED)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30)
    private ApplicationStatus status;

    // 가입 시작일
    @Column(name = "start_at")
    private LocalDateTime startAt;

    // 가입 만기일
    @Column(name = "close_at")
    private LocalDateTime closeAt;
    
    // 가입 시 약정 기본 금리
    @Column(name="base_rate_at_enroll", precision=5, scale=2)
    private BigDecimal baseRateAtEnroll;

    // 약정 기간(개월)
    @Column(name="term_months_at_enroll")
    private Integer termMonthsAtEnroll;
    
	// 우대금리 합산값(없으면 0)
    @Column(name="preferential_rate_annual", precision=5, scale=2)
    private BigDecimal preferentialRateAnnual;
    
	// 실제 적용 연이율 = 기본 + 우대
    @Column(name="effective_rate_annual", precision=5, scale=2)
    private BigDecimal effectiveRateAnnual;
    
    // 월 목표 걸음수
    @Column(name="walk_threshold_steps", length=10)
    private Long walkThresholdSteps;
}
