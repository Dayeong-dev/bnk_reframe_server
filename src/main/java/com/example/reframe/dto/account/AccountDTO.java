package com.example.reframe.dto.account;

import java.time.LocalDateTime;

import com.example.reframe.dto.auth.UserDTO;
import com.example.reframe.entity.account.AccountStatus;
import com.example.reframe.entity.account.AccountType;

import lombok.Data;

@Data
public class AccountDTO {
	private Long id;

    private UserDTO user;

    private String accountNumber;

    private String bankName;

    private AccountType accountType;  // DEMAND, PRODUCT

    private Long balance;
    
    private String accountName;

    private int isDefault; // 0: 아니오, 1: 예

    private AccountStatus status; // ACTIVE, CLOSED 등

    private LocalDateTime createdAt;
}
