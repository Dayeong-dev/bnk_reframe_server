package com.example.reframe.dto.auth;

import java.time.LocalDateTime;

import com.example.reframe.entity.auth.Gender;

import lombok.Data;

@Data
public class RealnameVerificationDTO {
	private Long id;

	private UserDTO user;

	private String name;

	private String phone;

	private String carrier;
	
	private String rrnFront;

	private Gender gender;

	private String ci;

	private LocalDateTime verifiedAt;
	
	private LocalDateTime expiresAt;
}
