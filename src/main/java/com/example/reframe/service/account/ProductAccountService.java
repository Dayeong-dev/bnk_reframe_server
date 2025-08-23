package com.example.reframe.service.account;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reframe.dto.account.ProductAccountDetail;
import com.example.reframe.entity.ProductApplication;
import com.example.reframe.entity.account.Account;
import com.example.reframe.entity.deposit.DepositPaymentLog;
import com.example.reframe.entity.deposit.DepositProductRate;
import com.example.reframe.entity.enroll.ProductApplicationInput;
import com.example.reframe.repository.ProductApplicationRepository;
import com.example.reframe.repository.deposit.DepositPaymentLogRepository;
import com.example.reframe.repository.deposit.DepositProductRateRepository;
import com.example.reframe.repository.enroll.ProductApplicationInputRepository;
import com.example.reframe.util.DepositPaymentLogMapper;
import com.example.reframe.util.DepositProductRateMapper;
import com.example.reframe.util.ProductApplicationInputMapper;
import com.example.reframe.util.ProductApplicationMapper;

@Service
@Transactional(readOnly = true)
public class ProductAccountService {

    private final ProductApplicationRepository productApplicationRepository;
    private final DepositPaymentLogRepository depositPaymentLogRepository;
    private final DepositProductRateRepository depositProductRateRepository;

    private final ProductApplicationMapper applicationMapper = new ProductApplicationMapper();
    private final DepositPaymentLogMapper paymentLogMapper = new DepositPaymentLogMapper();
    private final DepositProductRateMapper productRateMapper = new DepositProductRateMapper();

    public ProductAccountService(ProductApplicationRepository productApplicationRepository,
                                 DepositPaymentLogRepository depositPaymentLogRepository,
                                 DepositProductRateRepository depositProductRateRepository) {
        this.productApplicationRepository = productApplicationRepository;
        this.depositPaymentLogRepository = depositPaymentLogRepository;
        this.depositProductRateRepository = depositProductRateRepository;
    }

    public ProductAccountDetail getProductAccountDetail(Long accountId) {
        ProductApplication application = Optional
                .ofNullable(productApplicationRepository.findByProductAccount_Id(accountId))
                .orElseThrow(() -> new IllegalArgumentException("해당 계좌의 가입 정보가 없습니다. accountId=" + accountId));

        List<DepositPaymentLog> paymentLogs =
                depositPaymentLogRepository.findAllByApplication_IdOrderByRoundAsc(application.getId());

        List<DepositProductRate> productRates =
                depositProductRateRepository.findAllByProduct_ProductIdOrderByFromMonthAsc(
                        application.getProduct().getProductId()
                );


        ProductAccountDetail detail = new ProductAccountDetail();
        
        detail.setApplicationDTO(applicationMapper.toDTO(application));
        detail.setDepositPaymentLogDTOList(
                paymentLogs.stream().map(paymentLogMapper::toDTO).toList()
        );
        detail.setProductRateDTOList(
                productRates.stream().map(productRateMapper::toDTO).toList()
        );
        
        return detail;
    }
}