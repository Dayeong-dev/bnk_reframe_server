package com.example.reframe.entity.auth;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "bnk_corp_user")
@Data
public class CorporateUser {
	@Id
	private Long userId;
	
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;					// 회원 아이디(PFK)
	
	@NotNull
	private String businessNumber;		// 사업자등록번호
	
	@NotNull
	private String businessStartDate;	// 개업일자
	
	@NotNull
	private String ceoName;				// 대표자성명
	
}