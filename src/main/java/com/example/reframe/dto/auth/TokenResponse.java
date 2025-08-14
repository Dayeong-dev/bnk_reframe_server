package com.example.reframe.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {
	private String tokenType;		// 예) Bearer
	private String accessToken;
	private String refreshToken;
}
