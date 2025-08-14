package com.example.reframe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class CorporateDTO {

	private Long userId;

	@JsonProperty("b_no")
	private String businessNumber;

	@JsonProperty("start_dt")
	private String businessStartDate;

	@JsonProperty("p_nm")
	private String ceoName;

	@JsonProperty("p_nm2")
	private String ceoName2;

	@JsonProperty("b_nm")
	private String businessName;

	@JsonProperty("corp_no")
	private String corporateNumber;

	@JsonProperty("b_sector")
	private String businessSector;

	@JsonProperty("b_type")
	private String businessType;

	@JsonProperty("b_adr")
	private String businessAddress;

	private String name;
	private String email;
	private String phone;
	
}

