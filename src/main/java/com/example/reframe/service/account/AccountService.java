package com.example.reframe.service.account;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reframe.auth.CurrentUser;
import com.example.reframe.controller.api.account.AccountController.PayReq;
import com.example.reframe.domain.TransferCommand;
import com.example.reframe.dto.DailyCountDTO;
import com.example.reframe.dto.account.AccountDTO;
import com.example.reframe.entity.ProductApplication;
import com.example.reframe.entity.account.Account;
import com.example.reframe.entity.account.AccountStatus;
import com.example.reframe.entity.account.AccountType;
import com.example.reframe.entity.account.TransactionType;
import com.example.reframe.entity.auth.User;
import com.example.reframe.entity.deposit.DepositPaymentLog;
import com.example.reframe.entity.deposit.PaymentCycle;
import com.example.reframe.entity.deposit.PaymentStatus;
import com.example.reframe.repository.AccountRepository;
import com.example.reframe.repository.ProductApplicationRepository;
import com.example.reframe.repository.deposit.DepositPaymentLogRepository;
import com.example.reframe.service.OpenAIService;
import com.example.reframe.util.AccountMapper;
import com.example.reframe.util.AccountNumberGenerator;

@Service
public class AccountService {

    private final OpenAIService openAIService;
	
	private final AccountRepository accountRepository;
	private final ProductApplicationRepository applicationRepository;
	private final DepositPaymentLogRepository logRepository;
	private final TransactionService transactionService;
	private final CurrentUser currentUser;
	
	private static final ZoneId ZONE_SEOUL = ZoneId.of("Asia/Seoul");
	
	private final AccountMapper accountMapper = new AccountMapper();
	private final AccountNumberGenerator generator = new AccountNumberGenerator(); 
	
	public AccountService(CurrentUser currentUser, 
						  AccountRepository accountRepository, 
						  ProductApplicationRepository applicationRepository, 
						  DepositPaymentLogRepository logRepository, 
						  TransactionService transactionService, 
						  ProductAccountService productAccountService, OpenAIService openAIService) {
		this.openAIService = openAIService;
		this.accountRepository = accountRepository;
		this.applicationRepository = applicationRepository;
		this.logRepository = logRepository;
		this.transactionService = transactionService;
		this.currentUser = currentUser;
	}
	
	@Transactional 
	public Account createDefaultAccount(User user) { 
		String newNumber; 
		int tryCount = 0; 
		do { 
			newNumber = generator.newNumber(); 
			tryCount++; 
		} while (accountRepository.existsByAccountNumber(newNumber) && tryCount < 5); 
		
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
		
		Account result = accountRepository.save(acc);
		
		return result; 
	}
	

	@Transactional(readOnly=true)
	public List<AccountDTO> getAccounts(AccountType type) {
		Long userId = currentUser.id();
	    List<Account> accounts;
	    
	    if (type == null) {
	        accounts = accountRepository.findByUser_IdAndStatusOrderByIsDefaultDescCreatedAtDesc(userId, AccountStatus.ACTIVE);
	    } else {
	        accounts = accountRepository.findByUser_IdAndAccountTypeAndStatusOrderByIsDefaultDescCreatedAtDesc(userId, type, AccountStatus.ACTIVE);
	    }

	    List<AccountDTO> result = accounts.stream().map(accountMapper::toDTO).toList();
	    
	    return result;
	}
	
	@Transactional
	public void payForSaving(PayReq req) {
	  Long uid = currentUser.id();
	  if (uid == null) throw new IllegalArgumentException("로그인이 필요합니다.");

	  ProductApplication app = applicationRepository.findById(req.applicationId())
	      .orElseThrow(() -> new IllegalArgumentException("가입 정보를 찾을 수 없습니다."));

	  if (!uid.equals(app.getUser().getId())) throw new IllegalArgumentException("본인 상품이 아닙니다.");
	  if (!"적금".equals(app.getProduct().getCategory())
	      || app.getProduct().getPaymentCycle() != PaymentCycle.MONTHLY) {
	    throw new IllegalStateException("월납 적금이 아닙니다.");
	  }

	  // 1) 다음 미납 회차 (비관락 권장)
	  DepositPaymentLog next = logRepository
	      .findTop1ByApplication_IdAndStatusOrderByRoundAsc(app.getId(), PaymentStatus.UNPAID) // 아래 레포 쿼리 참고
	      .orElseThrow(() -> new IllegalStateException("납입 완료된 상품이거나 스케줄이 없습니다."));

	  // 2) 납입일 검증: 스케줄 예정일 사용
	  LocalDate todayKst = LocalDate.now(ZoneId.of("Asia/Seoul"));
	  if (next.getPaidAt() == null) {
	    throw new IllegalStateException("회차 예정일이 설정되어 있지 않습니다.");
	  }
	  LocalDate due = next.getPaidAt().toLocalDate(); // 가입 시 생성된 '예정일'
	  if (!todayKst.equals(due)) {
	    throw new IllegalStateException("납입일이 아닙니다. 이번 회차 납입일: " + due);
	  }

	  // 3) 계좌/잔액 검증
	  Account from = app.getFromAccount();
	  Account to   = app.getProductAccount();
	  if (from == null || to == null) throw new IllegalStateException("출금/상품 계좌 정보가 없습니다.");
	  if (!uid.equals(from.getUser().getId()) || !uid.equals(to.getUser().getId()))
	    throw new IllegalArgumentException("계좌 소유자 불일치");

	  long payAmount = next.getAmount();
	  if (from.getBalance() == null || from.getBalance() < payAmount)
	    throw new IllegalStateException("출금 계좌 잔액이 부족합니다.");

	  // 4) 이체
	  TransferCommand cmd = new TransferCommand();
	  cmd.setCorrelationId(java.util.UUID.randomUUID().toString());
	  cmd.setFromAccountId(from.getId());
	  cmd.setToAccountId(to.getId());
	  cmd.setTransactionType(TransactionType.DEPOSIT_PAYMENT);
	  cmd.setAmount(payAmount);
	  cmd.setOccurredAt(java.time.LocalDateTime.now());
	  transactionService.postTransfer(cmd);

	  // 5) 회차 완료
	  next.setStatus(PaymentStatus.PAID);
	  next.setPaidAt(java.time.LocalDateTime.now()); // 실제 납입시각으로 덮어쓰기(정책에 따라 별도 dueAt 필드 분리 고려)
	  logRepository.save(next);
	}

	@Transactional
    public void depositToAccount(Long targetAccountId, Long fromAccountId, Long amount) {
        Long uid = currentUser.id();
        if (uid == null) throw new IllegalStateException("로그인이 필요합니다.");
        if (targetAccountId == null || fromAccountId == null)
            throw new IllegalArgumentException("계좌 정보가 올바르지 않습니다.");
        if (amount == null || amount <= 0)
            throw new IllegalArgumentException("금액은 양수여야 합니다.");

        // 소유자/상태 검증
        Account to = accountRepository.findById(targetAccountId)
            .filter(a -> a.getUser() != null && uid.equals(a.getUser().getId()))
            .orElseThrow(() -> new IllegalArgumentException("입금 대상 계좌가 없거나 권한이 없습니다."));

        Account from = accountRepository.findById(fromAccountId)
            .filter(a -> a.getUser() != null && uid.equals(a.getUser().getId()))
            .orElseThrow(() -> new IllegalArgumentException("출금 계좌가 없거나 권한이 없습니다."));

        if (to.getId().equals(from.getId()))
            throw new IllegalArgumentException("같은 계좌로는 이체할 수 없습니다.");

        // 이체 실행 (분개 2행 + 잔액 갱신은 기존 TransactionService가 처리)
        var cmd = new TransferCommand();
        cmd.setCorrelationId(java.util.UUID.randomUUID().toString());
        cmd.setFromAccountId(from.getId());
        cmd.setToAccountId(to.getId());
        cmd.setTransactionType(TransactionType.TRANSFER); // ✅ 일반 이체
        cmd.setAmount(amount);
        cmd.setOccurredAt(java.time.LocalDateTime.now());

        transactionService.postTransfer(cmd);
    }

    /**
     * 최근 7일(오늘 포함) PRODUCT 가입자 수 일자별 집계
     */
    public List<DailyCountDTO> getProductSignupsLast7Days() {

        // 오늘(KST) 기준 날짜 범위 계산
        LocalDate today = LocalDate.now(ZONE_SEOUL);
        LocalDate startDate = today.minusDays(6);                 // 6일 전 (총 7일)
        LocalDateTime start = startDate.atStartOfDay();           // KST 00:00
        LocalDateTime endExclusive = today.plusDays(1).atStartOfDay(); // 내일 00:00

        // 1) 최근 7일 PRODUCT 행 조회
        List<Account> rows = accountRepository.findAllByAccountTypeAndCreatedAtBetween(
                AccountType.PRODUCT,
                start,
                endExclusive
        );

        // 2) 결과 버킷 초기화(날짜 오름차순, 0으로 채움)
        Map<LocalDate, Long> bucket = new LinkedHashMap<>();
        for (int i = 0; i < 7; i++) {
            bucket.put(startDate.plusDays(i), 0L);
        }

        // 3) createdAt -> LocalDate로 매핑하여 카운트(+1)
        for (Account a : rows) {
            // createdAt은 서버 로컬타임 기준 저장. 7일 범위로 이미 필터됨.
            LocalDate d = a.getCreatedAt().toLocalDate();
            // 방어: 혹시라도 범위 밖이면 스킵
            if (bucket.containsKey(d)) {
                bucket.put(d, bucket.get(d) + 1);
            }
        }

        // 4) DTO로 변환 (오름차순)
        List<DailyCountDTO> result = new ArrayList<>(7);
        bucket.forEach((d, c) -> result.add(new DailyCountDTO(d, c)));
        return result;
    }
	
	
	
}
