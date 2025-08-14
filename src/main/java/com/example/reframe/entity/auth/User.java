package com.example.reframe.entity.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bnk_user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;			// 회원 식별 번호
	
	@NotNull
	@Column(unique = true)
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
	private String usertype;	// 회원 타입		// P : 개인, C : 기업
	
	@NotNull
	private String role;		// 회원 역할		// 회원: ROLE_MEMBER
}
