package com.example.reframe.controller.api.account;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.reframe.dto.DailyCountDTO;
import com.example.reframe.dto.account.AccountDTO;
import com.example.reframe.dto.account.AccountTransactionDTO;
import com.example.reframe.dto.account.GlobalAvgResponse;
import com.example.reframe.dto.account.ProductAccountDetail;
import com.example.reframe.entity.account.AccountType;
import com.example.reframe.service.account.AccountService;
import com.example.reframe.service.account.AccountTransactionService;
import com.example.reframe.service.account.ProductAccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/mobile/account")
@Validated
public class AccountController {
	
	private final AccountService accountService;
	private final AccountTransactionService transactionService;
	private final ProductAccountService productAccountService;
	
	public AccountController(AccountService accountService, 
							 AccountTransactionService transactionService, 
							 ProductAccountService productAccountService) {
		this.accountService = accountService;
		this.transactionService = transactionService;
		this.productAccountService = productAccountService;
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
	
	@GetMapping("/detail/{id}")
	public ResponseEntity<ProductAccountDetail> getAccountList(@PathVariable("id") Long accountId) {

		ProductAccountDetail detail = productAccountService.getProductAccountDetail(accountId);
		
		System.out.println(detail.getApplicationDTO());
	        
	    return ResponseEntity.ok(detail);
	}
	


	@PostMapping(value = "/pay",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> pay(@RequestBody @Valid PayReq req) {
        accountService.payForSaving(req);
        return ResponseEntity.noContent().build();
    }

    public record PayReq(Long applicationId) {}
    
    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<Page<AccountTransactionDTO>> getTransactions(
            @PathVariable("accountId") Long accountId,
            @RequestParam(name="page", defaultValue = "0") int page,
            @RequestParam(name="size", defaultValue = "30") int size
    ) {
        var result = transactionService.getTransactions(accountId, page, size);
        return ResponseEntity.ok(result);
    }
    
    
    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<Void> deposit(
            @PathVariable("accountId") Long targetAccountId,
            @RequestBody DepositReq req
    ) {
        accountService.depositToAccount(targetAccountId, req.fromAccountId(), req.amount());
        return ResponseEntity.noContent().build();
    }

    public record DepositReq(Long fromAccountId, Long amount) {}

    @GetMapping("/api/stats/signups/7d")
    public List<DailyCountDTO> getSignups7d() {
        return accountService.getProductSignupsLast7Days();
    }
    
    /** 전체 사용자 평균 총자산 (집계값) */
    @GetMapping("/global-avg")
    public ResponseEntity<GlobalAvgResponse> globalAvg() {
        return ResponseEntity.ok(accountService.getGlobalAverageForUsers());
    }
    
}
