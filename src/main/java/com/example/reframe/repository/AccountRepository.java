package com.example.reframe.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.reframe.entity.account.Account;
import com.example.reframe.entity.account.AccountStatus;
import com.example.reframe.entity.account.AccountType;

import jakarta.persistence.LockModeType;

public interface AccountRepository extends JpaRepository<Account, Long> {

	boolean existsByAccountNumber(String newNumber);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select a from Account a where a.id = :id")
	public Account findByIdForUpdate(@Param("id") Long id);
	
	List<Account> findByUser_IdAndAccountTypeAndStatusOrderByIsDefaultDescCreatedAtDesc(
	    Long userId, AccountType accountType, AccountStatus status
	);
	
	Optional<Account> findFirstByUser_IdAndAccountTypeAndStatusAndIsDefaultTrue(
            Long userId, AccountType accountType, AccountStatus status
    );
	
	List<Account> findByUser_IdAndStatusOrderByIsDefaultDescCreatedAtDesc(
	        Long userId, AccountStatus status);

	 // 최근 7일 구간의 PRODUCT 계정들을 가져온 뒤 서비스에서 일자별로 집계
    List<Account> findAllByAccountTypeAndCreatedAtBetween(
            AccountType accountType,
            LocalDateTime startInclusive,
            LocalDateTime endExclusive
    );
}
