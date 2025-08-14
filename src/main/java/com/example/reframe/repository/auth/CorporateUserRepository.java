package com.example.reframe.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.auth.CorporateUser;

public interface CorporateUserRepository extends JpaRepository<CorporateUser, Long> {

}
