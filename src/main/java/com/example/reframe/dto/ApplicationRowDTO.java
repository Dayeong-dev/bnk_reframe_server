package com.example.reframe.dto;

import java.time.LocalDateTime;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class ApplicationRowDTO {
    private Long applicationId;
    private String status;           // STARTED/CLOSED/CANCELED
    private LocalDateTime startAt;
    private LocalDateTime closeAt;

    private Long userId;
    private String userName;
    private String userPhone;

    // 간단 마스킹용으로 풀 객체 대신 최소 필드만 전달
    private String productAccountNumber;
    private String productAccountBank;
    private String fromAccountNumber;
    private String fromAccountBank;
    private String maturityAccountNumber;
    private String maturityAccountBank;
}