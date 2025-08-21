package com.example.reframe.util;

import com.example.reframe.dto.account.AccountDTO;
import com.example.reframe.entity.account.Account;

public class AccountMapper {
	private UserMapper userMapper = new UserMapper();
	
	public Account toEntity(AccountDTO accountDTO) {
		if(accountDTO == null) return null;
		
		Account account = new Account();
		
		account.setId(accountDTO.getId());
		account.setUser(userMapper.toEntity(accountDTO.getUser()));
		account.setAccountNumber(accountDTO.getAccountNumber());
		account.setBankName(accountDTO.getBankName());
		account.setAccountType(accountDTO.getAccountType());
		account.setBalance(accountDTO.getBalance());
		account.setAccountName(accountDTO.getAccountName());
		account.setIsDefault(accountDTO.getIsDefault());
		account.setStatus(accountDTO.getStatus());
		account.setCreatedAt(accountDTO.getCreatedAt());
		
		return account;
	}
	
	public AccountDTO toDTO(Account account) {
		if(account == null) return null;
		
		AccountDTO accountDTO = new AccountDTO();
		
		accountDTO.setId(account.getId());
		accountDTO.setUser(userMapper.toDTO(account.getUser()));
		accountDTO.setAccountNumber(account.getAccountNumber());
		accountDTO.setBankName(account.getBankName());
		accountDTO.setAccountType(account.getAccountType());
		accountDTO.setBalance(account.getBalance());
		accountDTO.setAccountName(account.getAccountName());
		accountDTO.setIsDefault(account.getIsDefault());
		accountDTO.setStatus(account.getStatus());
		accountDTO.setCreatedAt(account.getCreatedAt());
		
		return accountDTO;
	}
}
