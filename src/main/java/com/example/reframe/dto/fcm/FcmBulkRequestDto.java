package com.example.reframe.dto.fcm;

import java.util.List;

import lombok.Data;

@Data
public class FcmBulkRequestDto {
	// 여러 그룹 전송 용도
	private String title;
	private String body;
	private List<String> groupCodes; 
}
