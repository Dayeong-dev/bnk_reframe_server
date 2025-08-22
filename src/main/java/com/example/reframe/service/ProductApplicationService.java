package com.example.reframe.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reframe.auth.CurrentUser;
import com.example.reframe.domain.TransferCommand;
import com.example.reframe.dto.account.ProductAccountDetail;
import com.example.reframe.dto.enroll.EnrollForm;
import com.example.reframe.entity.DepositProduct;
import com.example.reframe.entity.ProductApplication;
import com.example.reframe.entity.ProductApplication.ApplicationStatus;
import com.example.reframe.entity.account.Account;
import com.example.reframe.entity.account.AccountStatus;
import com.example.reframe.entity.account.AccountType;
import com.example.reframe.entity.account.TransactionType;
import com.example.reframe.entity.auth.User;
import com.example.reframe.entity.deposit.DepositPaymentLog;
import com.example.reframe.entity.deposit.PaymentCycle;
import com.example.reframe.entity.deposit.PaymentStatus;
import com.example.reframe.entity.enroll.ProductApplicationInput;
import com.example.reframe.repository.AccountRepository;
import com.example.reframe.repository.DepositProductRepository;
import com.example.reframe.repository.ProductApplicationRepository;
import com.example.reframe.repository.deposit.DepositPaymentLogRepository;
import com.example.reframe.repository.enroll.ProductApplicationInputRepository;
import com.example.reframe.service.account.TransactionService;
import com.example.reframe.util.AccountNumberGenerator;

@Service
public class ProductApplicationService {
	
	private final DepositProductRepository depositProductRepository;
	private final ProductApplicationRepository applicationRepository;
	private final ProductApplicationInputRepository applicationInputRepository;
	private final DepositPaymentLogRepository depositPaymentLogRepository;
	private final AccountRepository accountRepository;
	private final TransactionService transactionService;
	private final CurrentUser currentUser;
	
	private final AccountNumberGenerator accountNumberGenerator = new AccountNumberGenerator();
	
	public ProductApplicationService(DepositProductRepository depositProductRepository, 
									 ProductApplicationRepository applicationRepository, 
									 ProductApplicationInputRepository applicationInputRepository, 
									 DepositPaymentLogRepository depositPaymentLogRepository,
									 AccountRepository accountRepository, 
									 TransactionService transactionService, 
									 CurrentUser currentUser) {
		this.depositProductRepository = depositProductRepository;
		this.applicationRepository = applicationRepository;
		this.applicationInputRepository = applicationInputRepository;
		this.depositPaymentLogRepository = depositPaymentLogRepository;
		this.accountRepository = accountRepository;
		this.transactionService = transactionService;
		this.currentUser = currentUser;
	}
	
	
	// 상품 가입
	@Transactional
	public ProductApplication addApplication(Long productId, EnrollForm enrollForm) {
		
		// 비즈니스 거래 식별자 생성
		String correlationId = UUID.randomUUID().toString();
		
		
		Optional<DepositProduct> optionalProduct = depositProductRepository.findById(productId);
		
		if(optionalProduct.isEmpty()) throw new IllegalStateException("해당 상품이 존재하지 않습니다.");
		
		Long uid = currentUser.id();
		if(uid == null) throw new IllegalStateException("로그인이 필요합니다. ");
		
		
		// 가입할 상품 조회
		DepositProduct depositProduct = optionalProduct.get();
		
		// 가입 시작!
		ProductApplication productApplication = new ProductApplication();
		
		// 현재 로그인한 회원 정보 조회
		User user = new User();
		user.setId(uid);
		
		productApplication.setUser(user);
		productApplication.setProduct(depositProduct);
		
		// 새로 생성할 상품 계좌 생성
		Account account = new Account();
		
		try {
			account.setUser(user);		// 계좌 사용자 정보
			account.setAccountNumber(generateUniqueAccountNumber());		// 임시 계좌번호 값
			account.setAccountType(AccountType.PRODUCT);	// 계좌 종류(입출금, 상품)
			account.setBalance(0L);		// 잔액
			
			String groupName = enrollForm.getGroupName();
			if(groupName != null && !groupName.isBlank()) {
				account.setAccountName(groupName.trim());	// 입력된 모임명 있을 시 모임명으로 기입
			} else {
				account.setAccountName(depositProduct.getName());
			}
			
			account.setBankName("부산은행");				// 은행명(기본 부산은행)
			account.setIsDefault(0);					// 기본계좌 X
			account.setStatus(AccountStatus.ACTIVE);	// 계좌 상태(활성화)
			account.setCreatedAt(LocalDateTime.now());	// 계좌 생성 날짜(현재 시간)
			
			accountRepository.save(account);
		} catch(Exception e) {
			throw new IllegalStateException("계좌 개설 중 문제가 발생했습니다.");
		}
	
		// 생성한 상품 계좌 등록 
		productApplication.setProductAccount(account);
		
		// 출금 계좌 등록
		// 출금 계좌 입력 받을 때만
        if (enrollForm.getFromAccountId() != null) {
            Account from = accountRepository.findById(enrollForm.getFromAccountId())
                .filter(a -> a.getUser() != null && uid.equals(a.getUser().getId()))
                .filter(a -> a.getStatus() == AccountStatus.ACTIVE)
                .orElseThrow(() -> new IllegalArgumentException("출금 계좌가 유효하지 않습니다."));
            productApplication.setFromAccount(from);
        }
        
        // 만기 계좌 등록
     	// 만기 계좌 입력 받을 때만
        if (enrollForm.getMaturityAccountId() != null) {
            Account maturity = accountRepository.findById(enrollForm.getMaturityAccountId())
                .filter(a -> a.getUser() != null && uid.equals(a.getUser().getId()))
                .filter(a -> a.getStatus() == AccountStatus.ACTIVE)
                .orElseThrow(() -> new IllegalArgumentException("만기 계좌가 유효하지 않습니다."));
            productApplication.setMaturityAccount(maturity);
        }
		
		// 가입 상태 저장(현재 시작 상태)
		productApplication.setStatus(ApplicationStatus.STARTED);
		
		// 가입 시작일 저장
		productApplication.setStartAt(LocalDateTime.now());
		
		// 상품 가입 
		ProductApplication result = applicationRepository.save(productApplication);
		
		// 사용자 입력 데이터
		ProductApplicationInput productApplicationInput = new ProductApplicationInput();

		productApplicationInput.setApplication(result);	
		
		try {
			Long periodMonths = enrollForm.getPeriodMonths();	// 납입 기간
			Long paymentAmount = enrollForm.getPaymentAmount();	// 납입 금액
			Integer transferDate = enrollForm.getTransferDate();	// 이체일
			String groupName = enrollForm.getGroupName();		// 모임명
			String groupType = enrollForm.getGroupType();		// 모임 구분

			
			productApplicationInput.setBizMap1(periodMonths != null ? periodMonths.toString() : null);
			productApplicationInput.setBizMap2(paymentAmount != null ? paymentAmount.toString() : null);
			productApplicationInput.setBizMap3(transferDate != null ? transferDate.toString() : null);
			productApplicationInput.setBizMap4(null);
			productApplicationInput.setBizMap5(null);
			productApplicationInput.setBizMap6(null);
			productApplicationInput.setBizMap7(groupName);
			productApplicationInput.setBizMap8(groupType);
			
			applicationInputRepository.save(productApplicationInput);
			
		} catch(Exception e) {
			throw new IllegalStateException("사용자 입력 내역 등록 중 문제가 발생했습니다.");
		}
		
		if(depositProduct.getCategory().equals("예금")) {				// 예금 상품
			Long fromAccountId = enrollForm.getFromAccountId();
			Long amount = enrollForm.getPaymentAmount();
			LocalDateTime occurredAt = LocalDateTime.now();
			
			if(fromAccountId == null || amount == null) {
				throw new IllegalStateException("잘못된 요청입니다.");
			}
			
			TransferCommand transferCommand = new TransferCommand();
			
			transferCommand.setCorrelationId(correlationId);	// 비즈니스 거래 식별자
			transferCommand.setToAccountId(account.getId());	// 상품 계좌
			transferCommand.setFromAccountId(fromAccountId);	// 납입 계좌
			transferCommand.setTransactionType(TransactionType.DEPOSIT_PAYMENT);	// 거래 종류
			transferCommand.setAmount(amount);			// 납입 금액
			transferCommand.setOccurredAt(occurredAt);	// 거래 일시
			
			// 거래 처리(잔액 처리 및 로그 관리)
			transactionService.postTransfer(transferCommand);
			
		} else if(depositProduct.getCategory().equals("적금")) {		// 적금 상품
			
			PaymentCycle cycle = depositProduct.getPaymentCycle(); // 'MONTHLY' or 'DAILY'

		    if (cycle == PaymentCycle.MONTHLY) {
		        generateMonthlySchedule(result, enrollForm);     // 회차(월) UNPAID 생성
		    } else if (cycle == PaymentCycle.DAILY) {
		        // 일일형은 스케줄 미생성(지연 생성). 아무 것도 안함.
		    } else {
		        throw new IllegalStateException("지원하지 않는 납입 주기입니다: " + cycle);
		    }
			
		}
		
		return result;
	}
	
	@Transactional
	public void attendAndDeposit(Long applicationId, long amountFixed1000) {
	    ProductApplication app = applicationRepository.findById(applicationId)
	        .orElseThrow(() -> new IllegalArgumentException("가입 정보를 찾을 수 없습니다."));

	    // 가입 기간 내인지 체크(종료일 = startAt + period 개월 - 1일 23:59:59)
	    int periodMonths = extractPeriodMonths(app); // ProductApplicationInput 또는 form 저장분에서 가져오기
	    LocalDateTime start = app.getStartAt();
	    LocalDateTime endInclusive = start.plusMonths(periodMonths).minusNanos(1);
	    LocalDateTime now = LocalDateTime.now();
	    if (now.isAfter(endInclusive)) {
	        throw new IllegalStateException("가입 기간이 종료되었습니다.");
	    }

	    // 오늘의 round 계산 = 가입일 0시 기준 경과 '일' + 1
	    int round = computeDailyRound(start.toLocalDate(), now.toLocalDate());

	    // 1일 1회 중복 방지
	    if (depositPaymentLogRepository.existsByApplication_IdAndRound(applicationId, round)) {
	        throw new IllegalStateException("오늘은 이미 납입을 완료했습니다.");
	    }

	    // (선택) 커트오프/영업시간 제한이 있다면 여기서 체크

	    // 로그 생성(실제 납입 시각 기록)
	    DepositPaymentLog log = new DepositPaymentLog();
	    log.setApplication(app);
	    log.setRound(round);
	    log.setAmount(amountFixed1000);      // 73번 상품: 1,000원 고정
	    log.setPaidAt(now);
	    log.setStatus(PaymentStatus.PAID);
	    depositPaymentLogRepository.save(log);

	    // (선택) 출석 연속일 계산 → 리워드 발급 훅 호출
	    // rewardService.tryIssueStreakRewards(app.getId(), round);
	}

	private int computeDailyRound(LocalDate startDate, LocalDate today) {
	    long days = java.time.temporal.ChronoUnit.DAYS.between(startDate, today);
	    if (days < 0) throw new IllegalStateException("시스템 시간 오류");
	    return (int) days + 1; // 1일차부터 시작
	}

	private int extractPeriodMonths(ProductApplication application) {
	    return applicationInputRepository.findByApplication_Id(application.getId())
	            .map(ProductApplicationInput::getBizMap1)
	            .filter(s -> s != null && !s.isBlank())   // null/빈문자열 방어
	            .map(Integer::parseInt)
	            .orElse(6);
	}
	
	private String generateUniqueAccountNumber() {
	  for (int i = 0; i < 5; i++) {
	    String n = accountNumberGenerator.newNumber();
	    if (!accountRepository.existsByAccountNumber(n))
	    	return n;
	  }
	  throw new IllegalStateException("계좌번호 발급 실패");
	}
	
	private void generateMonthlySchedule(ProductApplication application, EnrollForm enrollForm) {
		Long periodMonths  = enrollForm.getPeriodMonths();   // 총 납입 개월 수
	    Long paymentAmount = enrollForm.getPaymentAmount();  // 회차별 납입 금액
	    Integer transferDate = enrollForm.getTransferDate(); // 매월 납입일(1~31)

	    if (periodMonths == null || paymentAmount == null || transferDate == null) {
	        throw new IllegalStateException("잘못된 요청입니다.");
	    }

	    // 가입 기준 시각
	    LocalDateTime base = application.getStartAt();
	    int startDay = base.getDayOfMonth();
	    int dueDay   = transferDate;

	    // 같은 날 커트오프(예: 17:00 이후면 다음 달로 미룸)
	    LocalDateTime cutoff = base.withHour(17).withMinute(0).withSecond(0).withNano(0);
	    boolean passedCutoffSameDay = (startDay == dueDay) && base.isAfter(cutoff);

	    // 이번 달 납입은 0, 다음 달 납입은 1
	    int firstOffset = (startDay < dueDay) ? 0 : (startDay > dueDay) ? 1 : (passedCutoffSameDay ? 1 : 0);

	    List<DepositPaymentLog> logs = new ArrayList<>();
	    
	    for (int i = 1; i <= periodMonths; i++) {
	        int monthOffset = firstOffset + (i - 1);

	        LocalDateTime targetMonth = base.plusMonths(monthOffset);
	        int dom = Math.min(dueDay, targetMonth.toLocalDate().lengthOfMonth());

	        // 납입 예정 시각 9시 고정
	        LocalDateTime dueDate = targetMonth.withDayOfMonth(dom)
	                                           .withHour(9).withMinute(0).withSecond(0).withNano(0);

	        DepositPaymentLog log = new DepositPaymentLog();
	        
	        log.setApplication(application);
	        log.setRound(i);
	        log.setAmount(paymentAmount);
	        log.setPaidAt(dueDate);                 // 예정일자
	        log.setStatus(PaymentStatus.UNPAID);    // 가입 시점엔 미납

	        logs.add(log);
	    }

	    depositPaymentLogRepository.saveAll(logs);
	}
}
