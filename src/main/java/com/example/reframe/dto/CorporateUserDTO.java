package com.example.reframe.dto;

import com.example.reframe.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CorporateUserDTO {
	
	private String username;
	
	private User user;
	
	@JsonProperty("b_no")
	private String businessNumber;		// 사업자등록번호
	
	@JsonProperty("start_dt")
	private String businessStartDate;	// 개업일자
	
	@JsonProperty("p_nm")
	private String ceoName;				// 대표자성명
	
	@JsonProperty("p_nm2")
	private String ceoName2;			// 대표자성명2
	
	@JsonProperty("b_nm")
	private String businessName;		// 상호
	
	@JsonProperty("corp_no")
	private String corporateNumber;		// 법인등록번호
	
	@JsonProperty("b_sector")
	private String businessSector;		// 주업태명
	
	@JsonProperty("b_type")
	private String businessType;		// 주종목명
	
	@JsonProperty("b_adr")
	private String businessAddress;		// 사업장주소
}