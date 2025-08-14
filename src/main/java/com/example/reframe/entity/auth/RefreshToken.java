package com.example.reframe.entity.auth;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "refresh_token")
@Data
public class RefreshToken {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull
    private User user;

    @NotNull
    @Column(unique = true)
    private String jti;		// 토큰 고유 ID

    @NotNull
    @Column(name = "issued_at")
    private Instant issuedAt;		// 발급 시간

    @NotNull
    @Column(name = "expires_at")
    private Instant expiresAt;		// 만료 시간

    @Column(name = "abs_expires_at")
    @NotNull
    private Instant absExpiresAt;	// 최대 연장 가능 시간

    @Column(name = "revoked_at")
    private Instant revokedAt;		// 폐기 시간
}
