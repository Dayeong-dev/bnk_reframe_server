package com.example.reframe.dto.account;

import java.time.LocalDateTime;

import com.example.reframe.entity.account.TransactionDirection;
import com.example.reframe.entity.account.TransactionType;

import lombok.Data;

@Data
public class AccountTransactionDTO {
    private Long id;
    
    private AccountDTO account;
    
    private AccountDTO counterpartyAccount;
    
    private Long amount;
    
    private TransactionDirection direction;
    
    private TransactionType transactionType;
    
    private LocalDateTime transactionAt;
}