package com.example.reframe.entity;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
