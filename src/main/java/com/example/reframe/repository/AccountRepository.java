package com.example.reframe.repository;

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

}
