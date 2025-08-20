package com.example.reframe.entity.fcm;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "fcm_history_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FcmHistoryDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FCM 발송 이력과의 연관 관계
    @ManyToOne
    @JoinColumn(name = "history_id")
    private FcmHistory history;

    // FCM 토큰과의 연관 관계
    @ManyToOne
    @JoinColumn(name = "token_id")
    private FcmToken token;

    private String status; // 발송 상태 (예: "success", "fail")

    @Column(columnDefinition = "CLOB")
    private String response; // Firebase에서 받은 응답 메시지

    private LocalDateTime createdAt;

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }
}