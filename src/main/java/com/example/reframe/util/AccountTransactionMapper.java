package com.example.reframe.util;

import com.example.reframe.dto.account.AccountTransactionDTO;
import com.example.reframe.entity.account.AccountTransaction;

public class AccountTransactionMapper {
	
	AccountMapper accountMapper = new AccountMapper();
	
	public AccountTransaction toEntity(AccountTransactionDTO transactionDTO) {
		AccountTransaction transaction = new AccountTransaction();
		
		transaction.setId(transactionDTO.getId());
		transaction.setAccount(accountMapper.toEntity(transactionDTO.getAccount()));
		transaction.setCounterpartyAccount(accountMapper.toEntity(transactionDTO.getCounterpartyAccount()));
		transaction.setAmount(transactionDTO.getAmount());
		transaction.setDirection(transactionDTO.getDirection());
		transaction.setTransactionType(transactionDTO.getTransactionType());
		transaction.setTransactionAt(transactionDTO.getTransactionAt());
		
		return transaction;
	}
	
	public AccountTransactionDTO toDTO(AccountTransaction transaction) {
		AccountTransactionDTO transactionDTO = new AccountTransactionDTO();
		
		transactionDTO.setId(transaction.getId());
		transactionDTO.setAccount(accountMapper.toDTO(transaction.getAccount()));
		transactionDTO.setCounterpartyAccount(accountMapper.toDTO(transaction.getCounterpartyAccount()));
		transactionDTO.setAmount(transaction.getAmount());
		transactionDTO.setDirection(transaction.getDirection());
		transactionDTO.setTransactionType(transaction.getTransactionType());
		transactionDTO.setTransactionAt(transaction.getTransactionAt());
		
		return transactionDTO;
	}
}
