package com.example.reframe.service.account;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.reframe.domain.TransferCommand;
import com.example.reframe.domain.TransferResult;
import com.example.reframe.entity.account.Account;
import com.example.reframe.entity.account.AccountStatus;
import com.example.reframe.entity.account.AccountTransaction;
import com.example.reframe.entity.account.TransactionDirection;
import com.example.reframe.entity.account.TransactionType;
import com.example.reframe.repository.AccountRepository;
import com.example.reframe.repository.account.AccountTransactionRepository;

@Service
public class TransactionService {
	private final AccountRepository accountRepository;
	private final TransactionLogWriter logWriter;
	
	public TransactionService(AccountRepository accountRepository, 
							  TransactionLogWriter logWriter) {
		this.accountRepository = accountRepository;
		this.logWriter = logWriter;
	}
	
	@Transactional
	public TransferResult postTransfer(TransferCommand cmd) {
		// 커맨드 유효성 확인
		validateCommand(cmd);
		
		Long fromAccountId = cmd.getFromAccountId();
		Long toAccountId = cmd.getToAccountId();
		
		// ID가 작은 순으로 락 진행
		Long firstId = Math.min(fromAccountId, toAccountId);
		Long secondId = Math.max(fromAccountId, toAccountId);
		
		// Lock 진행
		Account firstAccount = accountRepository.findByIdForUpdate(firstId);
		Account secondAccount = accountRepository.findByIdForUpdate(secondId);
		
		// 락 걸린 from 계좌, to 계좌 추출
		Account fromAccount = fromAccountId.equals(firstAccount.getId()) ? firstAccount : secondAccount;
		Account toAccount = toAccountId.equals(secondAccount.getId()) ? secondAccount : firstAccount;
		
		// 잠금 후 계좌 상태 확인
		validateDomain(cmd, fromAccount, toAccount);
		
		Long amount = cmd.getAmount();	// 거래 금액
		
		// 잔액 변경
		fromAccount.setBalance(fromAccount.getBalance() - amount);
		toAccount.setBalance(toAccount.getBalance() + amount);
		
		// 분개(분개는 회계 거래를 차변과 대변으로 나누어 기록하는 과정) 2행 기록
		logWriter.recordTransferEntries(cmd, fromAccount, toAccount);
		
		return new TransferResult(
			cmd.getCorrelationId(),
			fromAccount.getId(),
			toAccount.getId(),
			fromAccount.getBalance(),
			toAccount.getBalance(),
			cmd.getOccurredAt() == null ? LocalDateTime.now() : cmd.getOccurredAt()
		);
		
	}
	
	private void validateCommand(TransferCommand cmd) {
		if(cmd == null) throw new IllegalArgumentException("커맨드가 필요합니다.");
		
		if(cmd.getFromAccountId() == null || cmd.getToAccountId() == null) {
			throw new IllegalArgumentException("계좌를 찾을 수 없습니다.");
		}
		
		if(cmd.getFromAccountId().equals(cmd.getToAccountId())) {
			throw new IllegalArgumentException("입출금하려는 계좌는 달라야합니다.");
		}
		
		if(cmd.getAmount() == null || cmd.getAmount() <= 0L) {
			throw new IllegalArgumentException("거래 금액은 항상 양수여야 합니다.");
		}
		
		if(cmd.getTransactionType() == null) {
			throw new IllegalArgumentException("거래 유형은 필수 값 입니다.");
		}
		
		if(cmd.getCorrelationId() == null || cmd.getCorrelationId().isBlank()) {
			throw new IllegalArgumentException("비즈니스 거래 식별자(연관 아이디)는 필수 값 입니다.");
		}
	}
	
	private void validateDomain(TransferCommand cmd, Account fromAccount, Account toAccount) {
		if(fromAccount == null || toAccount == null) 
			throw new IllegalArgumentException("계좌를 찾을 수 없습니다.");
		
		if(fromAccount.getStatus() != AccountStatus.ACTIVE) {
			throw new IllegalArgumentException("출금 계좌가 비활성 계좌 입니다.");
		}
		
		if(toAccount.getStatus() != AccountStatus.ACTIVE) {
			throw new IllegalArgumentException("입금 계좌가 비활성 계좌 입니다.");
		}
		
		if(fromAccount.getBalance() == null || fromAccount.getBalance() < cmd.getAmount()) {
			throw new IllegalStateException("잔액이 충분하지 않습니다.");
		}

	}
	
}

@Component
class TransactionLogWriter {
	private final AccountTransactionRepository transactionRepository;
	
	public TransactionLogWriter(AccountTransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}
	
	// 로그 기록
	@Transactional(propagation = Propagation.MANDATORY)
	public void recordTransferEntries(TransferCommand transferCommand, Account fromAccount, Account toAccount) {
		Long amount = transferCommand.getAmount();	// 거래 금액
		
		TransactionType transactionType = transferCommand.getTransactionType();		// 거래 유형
		
		String correlationId = transferCommand.getCorrelationId();		// 비즈니스 거래 식별자(입,출금 내역 묶는 용도)
		
		// 출금 내역 등록
		AccountTransaction debitTransaction = new AccountTransaction();
		debitTransaction.setAccount(fromAccount);
		debitTransaction.setCounterpartyAccount(toAccount);
		debitTransaction.setAmount(amount);
		debitTransaction.setDirection(TransactionDirection.DEBIT);
		debitTransaction.setTransactionType(transactionType);
		debitTransaction.setTransactionAt(transferCommand.getOccurredAt() != null ? transferCommand.getOccurredAt() : LocalDateTime.now());
		debitTransaction.setTransferGroupId(correlationId);
		
		// 입금 내역 등록
		AccountTransaction creditTransaction = new AccountTransaction();
		creditTransaction.setAccount(toAccount);
		creditTransaction.setCounterpartyAccount(fromAccount);
		creditTransaction.setAmount(amount);
		creditTransaction.setDirection(TransactionDirection.CREDIT);
		creditTransaction.setTransactionType(transactionType);
		creditTransaction.setTransactionAt(transferCommand.getOccurredAt() != null ? transferCommand.getOccurredAt() : LocalDateTime.now());
		creditTransaction.setTransferGroupId(correlationId);
		
		transactionRepository.saveAll(List.of(debitTransaction, creditTransaction));
	}
}
