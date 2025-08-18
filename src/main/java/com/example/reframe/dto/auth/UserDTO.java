package com.example.reframe.dto.auth;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.example.reframe.entity.auth.Gender;

import lombok.Data;

@Data
public class UserDTO {
	
	private Long id;			// 회원 고유 식별 ID
	
	private String username;	// 회원 아이디
	
	private String password;	// 회원 비밀번호
	
	private String name;		// 회원 이름
	
	private String email;		// 회원 이메일
	
	private String phone;		// 회원 전화번호
	
	private String usertype;	// 회원 타입		// 1: 개인, 2: 기업
	
	private String role;		// 회원 역할		// 회원: ROLE_MEMBER
	
	private String gender;		// 회원 성별
	
	private String birth;		// 회원 생년월일
	
	public Gender toGenderEnum() {
		if (gender == null || gender.isBlank()) return null;
		
		return Gender.valueOf(gender);
	}
	
	public LocalDate toBirthDate() {
		if (birth == null || birth.isBlank()) return null;
		
		return LocalDate.parse(birth, DateTimeFormatter.ofPattern("yyyyMMdd"));
	}
}