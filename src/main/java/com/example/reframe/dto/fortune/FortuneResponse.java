package com.example.reframe.dto.fortune;

import java.util.List;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FortuneResponse {
    private String fortune;    // 한 문장 "~하기 좋은 하루"
    private String keyword;    // Enum 문자열
    private List<ProductBrief> products; // 정확히 2개
}
