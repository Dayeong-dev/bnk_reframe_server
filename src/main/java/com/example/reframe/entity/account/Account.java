package com.example.reframe.entity.account;

import java.time.LocalDateTime;

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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "account")
@Data
public class Account {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "account_number", nullable = false, unique = true, length = 16)
    private String accountNumber;

    @Column(name = "bank_name", nullable = false, length = 50)
    private String bankName;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false, length = 30)
    private AccountType accountType;  // DEMAND, PRODUCT

    private Long balance;
    
    @Column(name = "account_name", nullable = true)
    private String accountName;

    @Column(name = "is_default", nullable = false)
    private int isDefault; // 0: 아니오, 1: 예

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private AccountStatus status; // ACTIVE, CLOSED 등

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", nullable = true)
    private ProductType productType;

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
