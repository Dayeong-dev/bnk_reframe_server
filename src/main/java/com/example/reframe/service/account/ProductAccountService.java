package com.example.reframe.service.account;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reframe.auth.CurrentUser;
import com.example.reframe.dto.account.ProductAccountDetail;
import com.example.reframe.dto.deposit.DepositPaymentLogDTO;
import com.example.reframe.entity.ProductApplication;
import com.example.reframe.entity.account.Account;
import com.example.reframe.entity.deposit.DepositPaymentLog;
import com.example.reframe.entity.deposit.PaymentCycle;
import com.example.reframe.repository.ProductApplicationRepository;
import com.example.reframe.repository.deposit.DepositPaymentLogRepository;
import com.example.reframe.util.AccountMapper;
import com.example.reframe.util.DepositPaymentLogMapper;
import com.example.reframe.util.ProductApplicationMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductAccountService {

    private final ProductApplicationRepository productApplicationRepository;
    private final DepositPaymentLogRepository depositPaymentLogRepository;
    private final CurrentUser currentUser;

    private final AccountMapper accountMapper = new AccountMapper();
    private final ProductApplicationMapper applicationMapper = new ProductApplicationMapper();
    private final DepositPaymentLogMapper paymentLogMapper = new DepositPaymentLogMapper();
    
    static final int RATE_SCALE = 2;
    static final RoundingMode RM = RoundingMode.HALF_UP;
    static final BigDecimal DAYS_365 = BigDecimal.valueOf(365);


    public ProductAccountDetail getProductAccountDetail(Long accountId) {
    	
    	Long uid = currentUser.id();
        if (uid == null) throw new IllegalStateException("로그인이 필요합니다.");

        // 1) 소유자 검증 + Application 로딩	
        ProductApplication application = Optional
                .ofNullable(productApplicationRepository.findByProductAccount_Id(accountId))
                .orElseThrow(() -> new IllegalArgumentException("해당 계좌의 가입 정보가 없습니다. accountId=" + accountId));

        ProductAccountDetail detail = new ProductAccountDetail();
        
        // 가입 상품 정보 저장
        detail.setApplicationDTO(applicationMapper.toDTO(application));
        
        // 계좌 정보 저장
        if (application.getProductAccount() != null) {
            detail.setAccountDTO(accountMapper.toDTO(application.getProductAccount()));
        }
        
        // 옵션: 납입 로그
        List<DepositPaymentLogDTO> logs;
        
        var category = application.getProduct().getCategory(); // "예금"|"적금"|...

        if ("적금".equals(category)) {
            PaymentCycle cycle = application.getProduct().getPaymentCycle(); // MONTHLY|DAILY
            
            if (cycle == PaymentCycle.MONTHLY) {
                logs = depositPaymentLogRepository
                        .findAllByApplication_IdOrderByRoundAsc(application.getId())
                        .stream().map(paymentLogMapper::toDTO).toList();
            } else {
            	// 일일 납입 적금 처리
            	List<DepositPaymentLog> latest =
        		    depositPaymentLogRepository.findByApplication_Id(
        		        application.getId(),
        		        Sort.by(Sort.Direction.DESC, "round")
        		    );

        		logs = latest.stream().limit(30)
        		    .map(paymentLogMapper::toDTO)
        		    .sorted(java.util.Comparator.comparingInt(DepositPaymentLogDTO::getRound))
        		    .toList();
            }
        } else {
        	logs = List.of();
        }
        
        if ("예금".equals(category)) {
            long principal = Optional.ofNullable(application.getProductAccount())
                    .map(Account::getBalance).orElse(0L);

            BigDecimal rate = Optional.ofNullable(application.getEffectiveRateAnnual())
                    .orElse(BigDecimal.ZERO.setScale(RATE_SCALE, RM));

            InterestView view = calcTimeDepositViews(
                    principal,
                    rate,
                    application.getStartAt(),
                    application.getCloseAt()
            );

            detail.setProjectedInterestNow(view.projectedInterestNow());
            // 만기 없는 상품이면 null
            detail.setMaturityAmountProjected(application.getCloseAt() == null ? null : view.maturityAmountProjected());
        } else {
            // 적금/입출금자유 등은 비워둠
            detail.setProjectedInterestNow(null);
            detail.setMaturityAmountProjected(null);
        }
        
        detail.setDepositPaymentLogDTOList(logs);
        
//        List<DepositPaymentLog> paymentLogs =
//                depositPaymentLogRepository.findAllByApplication_IdOrderByRoundAsc(application.getId());
//
//        List<DepositProductRate> productRates =
//                depositProductRateRepository.findAllByProduct_ProductIdOrderByFromMonthAsc(
//                        application.getProduct().getProductId()
//                );
//        
//        detail.setDepositPaymentLogDTOList(
//                paymentLogs.stream().map(paymentLogMapper::toDTO).toList()
//        );
//        detail.setProductRateDTOList(
//                productRates.stream().map(productRateMapper::toDTO).toList()
//        );
        
        return detail;
    }
    
    private record InterestView(long projectedInterestNow, long maturityAmountProjected) {}

    private InterestView calcTimeDepositViews(
            long principalWon,                 // 원금(원, 정수)
            BigDecimal effectiveRateAnnual,    // 연이율 %, scale=2 (예: 3.20)
            LocalDateTime startAt,
            LocalDateTime closeAt              // null이면 만기 없음
    ) {
        if (effectiveRateAnnual == null) {
            effectiveRateAnnual = BigDecimal.ZERO.setScale(RATE_SCALE, RM);
        }
        LocalDate start = startAt.toLocalDate();
        LocalDate today = LocalDate.now();

        // 경과/총 일수
        long daysElapsed = Math.max(0, ChronoUnit.DAYS.between(start, today));
        long daysTotal   = (closeAt == null)
                ? daysElapsed
                : Math.max(daysElapsed, ChronoUnit.DAYS.between(start, closeAt.toLocalDate()));

        // r(%) → 소수로
        BigDecimal r = effectiveRateAnnual.divide(BigDecimal.valueOf(100), 10, RM);
        BigDecimal P = BigDecimal.valueOf(principalWon);

        BigDecimal elapsedFrac = BigDecimal.valueOf(daysElapsed).divide(DAYS_365, 10, RM);
        BigDecimal totalFrac   = BigDecimal.valueOf(daysTotal).divide(DAYS_365, 10, RM);

        // 세전 이자 (보수적으로 원 단위 내림)
        long interestNow  = P.multiply(r).multiply(elapsedFrac).setScale(0, RoundingMode.DOWN).longValue();
        long interestFull = P.multiply(r).multiply(totalFrac).setScale(0, RoundingMode.DOWN).longValue();

        long maturityAmount = principalWon + interestFull; // closeAt==null일 땐 호출부에서 null 처리

        return new InterestView(interestNow, maturityAmount);
    }
}