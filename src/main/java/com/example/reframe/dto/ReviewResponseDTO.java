package com.example.reframe.dto;

import lombok.*;
import java.util.Date;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class ReviewResponseDTO {
    private Long id;
    private Long productId;

    private Long authorId;      // ★ 추가
    private String authorName;

    private String content;
    private Integer rating;

    private Date createdAt;

    private boolean mine;       // ★ 추가 (현재 로그인 사용자가 쓴 글인지)
}
