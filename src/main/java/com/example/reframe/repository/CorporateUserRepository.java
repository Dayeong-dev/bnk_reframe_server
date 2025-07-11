package com.example.reframe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.CorporateUser;

public interface CorporateUserRepository extends JpaRepository<CorporateUser, String> {

}
