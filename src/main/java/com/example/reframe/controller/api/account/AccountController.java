package com.example.reframe.controller.api.account;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.reframe.auth.CurrentUser;
import com.example.reframe.dto.account.AccountDTO;
import com.example.reframe.entity.account.Account;
import com.example.reframe.entity.account.AccountStatus;
import com.example.reframe.entity.account.AccountType;
import com.example.reframe.repository.AccountRepository;
import com.example.reframe.util.AccountMapper;

@RestController
@RequestMapping("/mobile/account")
public class AccountController {
	
	@Autowired
	CurrentUser currentUser;
	
	@Autowired
	AccountRepository accountRepository;
	
	AccountMapper accountMapper = new AccountMapper();
	
	
	@GetMapping("/my")
	public ResponseEntity<List<AccountDTO>> getAccountList(@RequestParam(name = "type", required = false) AccountType type) {
		
		Long userId = currentUser.id();
	    List<Account> accounts;
	    
	    if (type == null) {
	        accounts = accountRepository.findByUser_IdAndStatusOrderByIsDefaultDescCreatedAtDesc(userId, AccountStatus.ACTIVE);
	    } else if (type != AccountType.DEMAND && type != AccountType.PRODUCT) {
	        return ResponseEntity.badRequest().build();
	    } else {
	        accounts = accountRepository.findByUser_IdAndAccountTypeAndStatusOrderByIsDefaultDescCreatedAtDesc(userId, type, AccountStatus.ACTIVE);
	    }

	    List<AccountDTO> result = accounts.stream().map(accountMapper::toDTO).toList();
	    System.out.println(result);
	        
	    return ResponseEntity.ok(result);
	}
}
