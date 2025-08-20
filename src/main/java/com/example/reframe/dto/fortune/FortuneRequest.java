package com.example.reframe.dto.fortune;

import java.time.LocalDateTime;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FortuneRequest {
    private String name;       // 사용자의 이름
    private String birthDate;  // yyyymmdd
    private String gender;     // "남" / "여"
    private LocalDateTime date; // 기준일 (프론트에서 now)
    private String invitedBy;  // 선택값
}
