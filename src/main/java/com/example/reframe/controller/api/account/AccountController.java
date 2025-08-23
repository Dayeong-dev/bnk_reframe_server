package com.example.reframe.controller.api.account;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.reframe.dto.account.AccountDTO;
import com.example.reframe.entity.account.AccountType;
import com.example.reframe.service.account.AccountService;

@RestController
@RequestMapping("/mobile/account")
public class AccountController {
	
	private final AccountService accountService;
	
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	
	@GetMapping("/my")
	public ResponseEntity<List<AccountDTO>> getAccountList(@RequestParam(name = "type", required = false) AccountType type) {
		try {
			List<AccountDTO> alist = accountService.getAccounts(type);
			
			return ResponseEntity.ok().body(alist);
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
//	@GetMapping("/detail/{id}")
//	public ResponseEntity<ProductAccountDetail> getAccountList(@PathVariable("id") Long accountId) {
//
//		ProductAccountDetail productAccountDetail = await ProductAccountService.getProductAccountDetail(accountId);
//		
//		System.out.println(productAccountDetail);
//	        
//	    return ResponseEntity.ok(productAccountDetail);
//	}
}
