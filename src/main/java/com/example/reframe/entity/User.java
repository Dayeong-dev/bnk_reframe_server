package com.example.reframe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "bnk_user")
@Data
public class User {
	@Id
	private String username;	// 회원 아이디
	
	private String password;	// 회원 비밀번호
	
	private String name;		// 회원 이름
	
	private String email;		// 회원 이메일
	
	private String phone;		// 회원 전화번호
	
	private String usertype;	// 회원 타입		// 1: 개인, 2: 기업
	
	private String role;		// 회원 역할		// 회원: ROLE_MEMBER
}
