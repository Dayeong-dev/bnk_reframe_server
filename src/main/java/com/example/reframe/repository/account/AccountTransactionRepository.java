package com.example.reframe.repository.account;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.account.AccountTransaction;

public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {

	List<AccountTransaction> findByAccount_IdAndAccount_User_Id(Long id, Long uid);

	Page<AccountTransaction> findByAccount_IdAndAccount_User_Id(Long id, Long uid, Pageable pageable);

}
