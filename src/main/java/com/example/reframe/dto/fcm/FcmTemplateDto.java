package com.example.reframe.dto.fcm;

import lombok.Data;

@Data
public class FcmTemplateDto {
	
	private Long id; // 템플릿 식별자 (수정 시 필요)
	private String groupCode; // 발송 대상 그룹 코드
	private String title; // 알림 제목
	private String body; // 알림 내용
	private String cron; // cron 표현식 (예: "0 0 9 * * *")
	private boolean active; // 사용 여부 (true: 사용 / false: 비활성화)
	
}

