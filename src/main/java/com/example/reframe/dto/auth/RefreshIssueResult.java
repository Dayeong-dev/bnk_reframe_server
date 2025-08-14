package com.example.reframe.dto.auth;

import java.time.Instant;

import lombok.Data;

@Data
public class RefreshIssueResult {
	private String jti;		// JWT ID
	private Instant iss;	// 발급 시간
	private Instant exp;	// 만료 시간
	private Instant abs;	// 절대 상한 만료 시간
}
