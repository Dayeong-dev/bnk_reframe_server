package com.example.reframe.service.auth;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reframe.dto.auth.RefreshIssueResult;
import com.example.reframe.dto.auth.TokenResponse;
import com.example.reframe.dto.auth.UserDTO;
import com.example.reframe.entity.auth.RefreshToken;
import com.example.reframe.repository.auth.RefreshTokenRepository;
import com.example.reframe.util.JWTUtil;
import com.example.reframe.util.UserMapper;

@Service
public class RefreshTokenService {
	
	private final RefreshTokenRepository refreshTokenRepository;
	private final JWTUtil jwtUtil;
	private final JwtDecoder jwtDecoder;
	
	private final UserMapper userMapper = new UserMapper();
	
    public static final Long ACCESS_EXPIRATION_MS = 1000 * 60 * 30L;					// 1000 * 60 * 30L == 30분
    public static final Long REFRESH_EXPIRATION_MS = 1000 * 60 * (60L * 24 * 14);		// 1000 * 60 * (60L * 24 * 14) == 14일
    public static final Long REFRESH_ABS_EXPIRATION_MS = 1000 * 60 * (60L * 24 * 30);	// 1000 * 60 * (60L * 24 * 30) == 30일
	
	public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, JWTUtil jwtUtil, JwtDecoder jwtDecoder) {
		this.refreshTokenRepository = refreshTokenRepository;
		this.jwtUtil = jwtUtil;
		this.jwtDecoder = jwtDecoder;
	}
	
	@Transactional
	public RefreshIssueResult issue(UserDTO userDTO) {
		String newJti = randomJti();	// jti 생성
		Instant now = Instant.now();	// 현재 시간
		Instant exp = now.plusMillis(REFRESH_EXPIRATION_MS);
		Instant abs = now.plusMillis(REFRESH_ABS_EXPIRATION_MS);
		
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setJti(newJti);
		refreshToken.setIssuedAt(now);
		refreshToken.setExpiresAt(min(exp, abs));
		refreshToken.setAbsExpiresAt(abs);
		refreshToken.setUser(userMapper.toEntity(userDTO));
		
		refreshTokenRepository.save(refreshToken);
		
		RefreshIssueResult result = new RefreshIssueResult();
		result.setJti(newJti);
		result.setIss(now);
		result.setExp(exp);
		result.setAbs(abs);
		
		return result;
	}
	
	@Transactional
    public TokenResponse refresh(String refreshToken) {
		Jwt jwt;
		
		try {
			jwt = jwtDecoder.decode(refreshToken);
		} catch(JwtException e) {	
			throw new RuntimeException("유효하지 않은 Refresh 토큰입니다.");
		}
		
		if(!jwt.getClaimAsString("typ").equals("refresh")) {
			throw new RuntimeException("Refresh 토큰이 아닙니다.");
		}
		
		Instant now = Instant.now();
		
        Long userId = Long.valueOf(jwt.getSubject());
        String jti = jwt.getId();

        RefreshToken currRefressToken = refreshTokenRepository.findByUser_IdAndJti(userId, jti)
                .orElseThrow(() -> new RuntimeException("Refresh 토큰이 없습니다."));

        // 유효성 검사 : 만료/폐기/절대상한
        if (currRefressToken.getRevokedAt() != null) throw new RuntimeException("폐기된 Refresh 토큰입니다.");
        if (!currRefressToken.getExpiresAt().isAfter(now)) throw new RuntimeException("Refresh 토큰이 만료되었습니다.");
        if (!currRefressToken.getAbsExpiresAt().isAfter(now)) throw new RuntimeException("Refresh 토큰이 절대상한을 초과했습니다.");

        // 회전(롤링): 현재 사용중인 Refresh 토큰 폐기 + 새 Refresh 토큰 발급 및 행 추가
        String newJti = randomJti();
        
        Instant exp = now.plusMillis(REFRESH_EXPIRATION_MS);
        Instant abs = currRefressToken.getAbsExpiresAt();		// 기존 DB에 저장되어 있는 절대 상한 유지
        
        // 현재 Refresh 토큰 폐기
        currRefressToken.setRevokedAt(now);
        refreshTokenRepository.save(currRefressToken);

        RefreshToken nextRefreshToken = new RefreshToken();
        
        nextRefreshToken.setUser(currRefressToken.getUser());
        nextRefreshToken.setJti(newJti);
        nextRefreshToken.setIssuedAt(now);
        nextRefreshToken.setExpiresAt(min(exp, abs));
        nextRefreshToken.setAbsExpiresAt(abs);
        
        refreshTokenRepository.save(nextRefreshToken);

        String access = jwtUtil.createAccessToken(userMapper.toDTO(currRefressToken.getUser()), ACCESS_EXPIRATION_MS);
        String refresh = jwtUtil.createRefreshToken(newJti, userMapper.toDTO(currRefressToken.getUser()), REFRESH_EXPIRATION_MS);
        
        return new TokenResponse("Bearer", access, refresh);
    }
	
	private static String randomJti() {
		SecureRandom secureRandom = new SecureRandom();
        byte[] buf = new byte[16];
        
        secureRandom.nextBytes(buf);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(buf);
    }
	
	static Instant min(Instant a, Instant b) {
		return a.isBefore(b) ? a : b;
	}

}
