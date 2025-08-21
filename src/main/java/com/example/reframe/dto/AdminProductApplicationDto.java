package com.example.reframe.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AdminProductApplicationDto {

	private String userName;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 프론트가 그대로 출력
	private LocalDateTime startAt;

	private String status; // IN_PROGRESS / DONE / CANCEL 등
	private String productAccountNumber; // 상품 계좌
	private String fromAccountNumber; // 출금 계좌
}
