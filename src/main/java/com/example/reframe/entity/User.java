package com.example.reframe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "bnk_user")
@Data
public class User {
	@Id
	private String username;	// 회원 아이디
	
	@NotNull
	private String password;	// 회원 비밀번호
	
	@NotNull
	private String name;		// 회원 이름
	
	@NotNull
	private String email;		// 회원 이메일
	
	@NotNull
	private String phone;		// 회원 전화번호
	
	@NotNull
	@Size(min = 1, max = 1)
	private String usertype;	// 회원 타입		// 1: 개인, 2: 기업
	
	@NotNull
	private String role;		// 회원 역할		// 회원: ROLE_MEMBER
}
