package com.example.reframe.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.reframe.entity.Card;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardApprovalRequestDTO {
	private Long id;                       // 결재 요청 ID
    private Long cardId;                   // 카드 ID
    private String cardName;               // 카드명 (뷰 표시용)
    private String requestedBy;            // 요청자
    private String approvedBy;             // 승인자
    private LocalDateTime requestedAt;     // 요청 시간
    private LocalDateTime approvedAt;      // 승인 시간
    private String status;                 // 상태값 (PENDING, APPROVED, REJECTED)
    private String rejectionReason;        // 반려 사유 (nullable)
    private List<CardApprovalRequestDetailDTO> details; // 상세 변경 내역 리스트
}
