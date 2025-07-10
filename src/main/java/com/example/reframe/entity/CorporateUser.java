package com.example.reframe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "bnk_corp_user")
@Data
public class CorporateUser {
	@Id
	private String username;
	
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "username")
	private User user;					// 회원 아이디(PFK)
	
	private String businessNumber;		// 사업자등록번호
	private String businessStartDate;	// 개업일자
	private String ceoName;				// 대표자성명
	
}