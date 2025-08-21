package com.example.reframe.dto.enroll;

import lombok.Data;

@Data
public class EnrollForm {
	private Long periodMonths;
	private Long paymentAmount;
	private Integer transferDate;
	
	private String groupName;
	private String groupType;

	private Long fromAccountId;
	private Long maturityAccountId;
}