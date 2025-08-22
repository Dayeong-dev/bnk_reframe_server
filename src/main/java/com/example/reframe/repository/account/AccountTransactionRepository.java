package com.example.reframe.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.account.AccountTransaction;

public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {

}
