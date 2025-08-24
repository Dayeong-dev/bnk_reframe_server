package com.example.reframe.entity.account;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;


@Entity
@Table(name = "account_transaction",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_account_transaction",
            columnNames = {"transfer_group_id", "account_id", "direction"}
        )
    }
)
@Data
public class AccountTransaction {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // 거래 고유 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;	// 거래가 발생한 계좌

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "counterparty_account_id")
    private Account counterpartyAccount;	// 상대 계좌 (이체/납입 등)

    @Column(nullable = false)
    private Long amount;			// 거래 금액 (항상 양수, 방향은 direction으로 구분)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionDirection direction;		// 거래 방향 (DEBIT 출금, CREDIT 입금)

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false, length = 30)
    private TransactionType transactionType;	// 거래 유형 (TRANSFER, DEPOSIT, PAYMENT 등)

    @Column(name = "transfer_group_id", nullable = false, length = 36)
    private String transferGroupId;			// 비즈니스 거래 식별자
    
    @Column(nullable = false)
    private LocalDateTime transactionAt;	// 거래 발생 일시
}
