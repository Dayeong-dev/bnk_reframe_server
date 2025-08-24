package com.example.reframe.service.account;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reframe.auth.CurrentUser;
import com.example.reframe.dto.account.AccountTransactionDTO;
import com.example.reframe.entity.account.Account;
import com.example.reframe.entity.account.AccountTransaction;
import com.example.reframe.repository.AccountRepository;
import com.example.reframe.repository.account.AccountTransactionRepository;
import com.example.reframe.util.AccountTransactionMapper;

@Service
@Transactional(readOnly = true)
public class AccountTransactionService {
	
	private static final int MAX_SIZE = 100;

    private final AccountRepository accountRepository;
    private final AccountTransactionRepository txRepository;
    private final CurrentUser currentUser;
    
    private final AccountTransactionMapper accountTransactionMapper = new AccountTransactionMapper();

    public AccountTransactionService(AccountRepository accountRepository,
                                          AccountTransactionRepository txRepository,
                                          CurrentUser currentUser) {
        this.accountRepository = accountRepository;
        this.txRepository = txRepository;
        this.currentUser = currentUser;
    }
    
    public Page<AccountTransactionDTO> getTransactions(Long accountId, int page, int size) {
        Long uid = currentUser.id();
        if (uid == null) throw new IllegalStateException("로그인이 필요합니다.");

        // 1) 소유자 검증 (원하면 ACTIVE 상태 체크도 추가)
        Account account = accountRepository.findById(accountId)
            .filter(a -> a.getUser() != null && Objects.equals(a.getUser().getId(), uid))
            .orElseThrow(() -> new IllegalArgumentException("계좌가 없거나 권한이 없습니다."));

        // 2) 페이징/정렬: 최신거래 우선
        int safePage = Math.max(0, page);
        int safeSize = Math.max(1, Math.min(size, MAX_SIZE));
        Sort sort = Sort.by(Sort.Direction.DESC, "transactionAt")
                        .and(Sort.by(Sort.Direction.DESC, "id"));
        Pageable pageable = PageRequest.of(safePage, safeSize, sort);

        // 3) 조회
        // 레포가 다음 시그니처를 가졌다고 가정:
        // Page<AccountTransaction> findByAccount_IdAndAccount_User_Id(Long accountId, Long userId, Pageable pageable);
        Page<AccountTransaction> entities =
            txRepository.findByAccount_IdAndAccount_User_Id(account.getId(), uid, pageable);

        // 4) DTO 매핑
        return entities.map(accountTransactionMapper::toDTO);
    }
}
