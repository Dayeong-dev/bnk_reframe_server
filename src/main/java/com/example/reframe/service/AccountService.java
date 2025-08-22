package com.example.reframe.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reframe.entity.account.Account;
import com.example.reframe.entity.account.AccountStatus;
import com.example.reframe.entity.account.AccountType;
import com.example.reframe.entity.auth.User;
import com.example.reframe.repository.AccountRepository;
import com.example.reframe.util.AccountNumberGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
	private final AccountRepository accountRepo; 
	private final AccountNumberGenerator generator = new AccountNumberGenerator(); 
	
	@Transactional 
	public Account createDefaultAccount(User user) { 
		String newNumber; 
		int tryCount = 0; 
		do { 
			newNumber = generator.newNumber(); 
			tryCount++; 
		} while (accountRepo.existsByAccountNumber(newNumber) && tryCount < 5); 
		
		if (tryCount == 5) { 
			throw new IllegalStateException("계좌번호 생성 실패: 중복 초과"); 
		} 
		Account acc = new Account(); 
		
		acc.setUser(user); 
		acc.setAccountNumber(newNumber); 
		acc.setBankName("부산은행");
		acc.setAccountName(user.getName() + "님의 통장");
		acc.setAccountType(AccountType.DEMAND); // 입출금 기본 계좌 
		acc.setBalance(10_000_000L); // 초기 잔액 더미 (예: 1000만 원) 
		acc.setIsDefault(1);	// 기본 계좌
		acc.setStatus(AccountStatus.ACTIVE);
		
		Account result = accountRepo.save(acc);
		
		return result; 
	}
}
