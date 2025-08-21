package com.example.reframe.dto.enroll;

import java.time.LocalDateTime;

import com.example.reframe.dto.DepositProductDTO;
import com.example.reframe.dto.account.AccountDTO;
import com.example.reframe.dto.auth.UserDTO;
import com.example.reframe.entity.ProductApplication.ApplicationStatus;

import lombok.Data;

@Data
public class ProductApplicationDTO {
	private Long id; // 상품 가입 ID

    // 회원 고유 ID (FK)
    private UserDTO user;

    // 상품 ID (FK)
    private DepositProductDTO product;

    // 개설된 상품 계좌 ID (FK)
    private AccountDTO productAccount;

    // 납입 출금 계좌 ID (FK, nullable = true)
    private AccountDTO fromAccount;

    // 만기 원리금 입금 계좌 ID (FK, nullable = true)
    private AccountDTO maturityAccount;

    // 가입 상태 (STARTED, CLOSED, CANCELED)
    private ApplicationStatus status;

    // 가입 시작일
    private LocalDateTime startAt;

    // 가입 만기일
    private LocalDateTime closeAt;
}
