package com.example.reframe.controller.api.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reframe.dto.auth.TokenResponse;
import com.example.reframe.dto.auth.UserDTO;
import com.example.reframe.service.auth.RefreshTokenService;
import com.example.reframe.service.auth.UserService;

@RestController
@RequestMapping("/mobile/auth")
@CrossOrigin(origins = "*")
public class AuthController {
	
	private final UserService userService;
	private final RefreshTokenService refreshTokenService;
	
	public AuthController(UserService userService, RefreshTokenService refreshTokenService) {
		this.userService = userService;
		this.refreshTokenService = refreshTokenService;
	}
	
	
	@PostMapping("/signin")
	private ResponseEntity<TokenResponse> signin(@RequestBody UserDTO userDTO) {		
		try {
			TokenResponse tokenResponse = userService.signin(userDTO);
			
			String accessToken = tokenResponse.getAccessToken();
			String tokenType = tokenResponse.getTokenType();
			tokenResponse.setAccessToken(tokenType + " " + accessToken);		// Bearer 추가
			
			return ResponseEntity.status(HttpStatus.OK).body(tokenResponse);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	@PostMapping("/signup")
	private ResponseEntity<Void> signup(@RequestBody UserDTO userDTO) {
		System.out.println(userDTO);
		
		UserDTO result = userService.signup(userDTO);

		if(result == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PostMapping("/refresh")
	private ResponseEntity<TokenResponse> refresh(@RequestBody TokenResponse receiveToeken) {
		try {			
			TokenResponse tokenResponse = refreshTokenService.refresh(receiveToeken.getRefreshToken());
			
			String accessToken = tokenResponse.getAccessToken();
			String tokenType = tokenResponse.getTokenType();
			tokenResponse.setAccessToken(tokenType + " " + accessToken);		// Bearer 추가
			
			return ResponseEntity.status(HttpStatus.OK).body(tokenResponse);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}
