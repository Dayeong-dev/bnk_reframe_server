package com.example.reframe.repository.auth;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.auth.RealnameVerification;

public interface RealnameVerificationRepository extends JpaRepository<RealnameVerification, Long> {

	Optional<RealnameVerification> findTopByUser_IdOrderByVerifiedAtDesc(Long uid);

}
