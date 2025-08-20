package com.example.reframe.entity.fcm;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "fcm_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FcmHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // 알림 제목

    @Column(columnDefinition = "CLOB")
    private String body; // 알림 내용

    private String targetGroup; // 발송 대상 그룹 코드 (예: "event", "premium")

    private Integer sentCount; // 발송 대상 토큰 수

    private LocalDateTime createdAt; // 발송 시각

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }
}