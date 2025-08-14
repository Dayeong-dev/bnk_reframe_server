package com.example.reframe.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.reframe.dto.auth.UserDTO;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
public class JWTUtil {
	
	private final SecretKey secretKey;
//	private final JwtParser parser;

	private final String issuer;
	
    public JWTUtil(@Value("${spring.jwt.secret}") String secret, @Value("${spring.jwt.issuer}") String issuer) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), 
        								   Jwts.SIG.HS256.key().build().getAlgorithm());
        
//        this.parser = Jwts.parser().verifyWith(secretKey).clockSkewSeconds(60).build();
        this.issuer = issuer;

        System.out.println("secretKey: " + secretKey.toString() + ", algorithm: " + secretKey.getAlgorithm());
    }
    
    public enum TokenStatus { VALID, EXPIRED, INVALID }
    
    // 토큰에서 사용자 정보 추출
//    public String getUserId(String token) {
//    	String userId = parseAndValidate(token).getPayload().getSubject();
//    	
//    	return userId;
//    }

//	public String getRole(String token) {
//		Claims claim = parseAndValidate(token).getPayload();
//		
//		return claim.get("role", String.class);
//	}
	
//	public Jws<Claims> parseAndValidate(String token) {
//		return parser.parseSignedClaims(token);
//	}

//	public void printIatAndExp(String token) {
//		Jws<Claims> jws = parser.parseSignedClaims(token);	// 서명 검증 + 파싱
//		Claims claims = jws.getPayload();
//
//		Date iat = claims.getIssuedAt();
//		Date exp = claims.getExpiration();
//
//		System.out.println("iat: " + iat);
//		System.out.println("exp: " + exp);
//	}

//	public TokenStatus isValid(String token) {
//		try {
//			parser.parseSignedClaims(token);
//			return TokenStatus.VALID;
//		} catch (ExpiredJwtException e) {
//			System.out.println("토큰 만료");			
//			return TokenStatus.EXPIRED;
//		} catch (Exception e) {
//			System.out.println("토큰 인증 오류");
//			return TokenStatus.INVALID;
//		}
//	}
    
    public String createAccessToken(UserDTO userDTO, Long expirationMs) {
    	
    	String token = Jwts.builder()
    			.issuer(issuer)
    			.subject(userDTO.getId().toString())
				.claim("roles", List.of(userDTO.getRole()))
				.claim("typ", "access")
    			.issuedAt(new Date(System.currentTimeMillis()))
    			.expiration(new Date(System.currentTimeMillis() + expirationMs))
    			.signWith(secretKey)
    			.compact();
    	
    	return token;
    }
    
    // 리프레시 토큰 생성
    public String createRefreshToken(String jti, UserDTO userDTO, Long expirationMs) {
    	
    	String token = Jwts.builder()
    			.id(jti)
    			.issuer(issuer)
    			.subject(userDTO.getId().toString())
    			.claim("typ", "refresh")
    			.issuedAt(new Date(System.currentTimeMillis()))
    			.expiration(new Date(System.currentTimeMillis() + expirationMs))
    			.signWith(secretKey)
    			.compact();
    	
    	return token;
    }
}
