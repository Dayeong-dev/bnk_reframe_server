package com.example.reframe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BusinessApiResult {
	@JsonProperty("b_no")
	private String businessNumber;			// 사업자등록번호
	
	@JsonProperty("valid")
	private String valid;					// 진위확인 결과 코드(01: Valid, 02: Invalid)
	
	@JsonProperty("valid_msg")
	private String validMsg;				// 진위확인 결과 메세지
	
	@JsonProperty("request_param")
	private CorporateUserDTO requestParam;	// 사업자등록번호 진위확인 정보
}