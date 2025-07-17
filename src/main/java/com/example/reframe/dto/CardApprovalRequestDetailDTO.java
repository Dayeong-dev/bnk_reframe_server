package com.example.reframe.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardApprovalRequestDetailDTO {
	private Long id;           // 상세 요청 ID
    private String fieldName;  // 변경된 필드명
    private String oldValue;   // 기존 값
    private String newValue;   // 변경 값
}