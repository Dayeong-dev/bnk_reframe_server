package com.example.reframe.entity.auth;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "realname_verification")
@Data
public class RealnameVerification {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	
	 // 회원 FK
	 @ManyToOne(fetch = FetchType.LAZY, optional = false)
	 @JoinColumn(name = "user_id", nullable = false)
	 private User user;
	
	 @Column(name = "name", length = 100)
	 private String name;
	
	 @Column(name = "phone", length = 20)
	 private String phone;
	
	 @Column(name = "carrier", length = 30)
	 private String carrier;
	
	 // 주민등록번호 앞자리(생년월일 6자리)
	 @Column(name = "rrn_front", length = 6)
	 private String rrnFront;
	
	 // 성별: 'M' / 'F' 등 1글자 가정
	 @Enumerated(EnumType.STRING)
	 @Column(name = "gender", length = 1)
	 private Gender gender;
	
	 @Column(name = "ci", length = 200)
	 private String ci;
	
	 @Column(name = "verified_at")
	 private LocalDateTime verifiedAt;
	
	 @Column(name = "expires_at")
	 private LocalDateTime expiresAt;

}

