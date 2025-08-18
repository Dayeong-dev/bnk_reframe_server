package com.example.reframe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.account.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	boolean existsByAccountNumber(String newNumber);

}
