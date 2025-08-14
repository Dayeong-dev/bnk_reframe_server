package com.example.reframe.dto.auth;

import java.time.Instant;

import com.example.reframe.entity.auth.User;

import lombok.Data;

@Data
public class RefreshTokenDTO {
	
	private Long id;

    private User user;

    private String jti;				// 토큰 고유 ID

    private Instant issuedAt;		// 발급 시간

    private Instant expiresAt;		// 만료 시간

    private Instant absExpiresAt;	// 최대 연장 가능 시간

    private Instant revokedAt;		// 폐기 시간

    public boolean isActive(Instant now) {
        return revokedAt == null && expiresAt.isAfter(now) && absExpiresAt.isAfter(now);
    }
}
