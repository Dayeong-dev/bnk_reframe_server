// src/main/java/com/example/reframe/dto/ReviewResponseDTO.java
package com.example.reframe.dto;

import java.util.Date;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ReviewResponseDTO {
    private Long id;
    private Long productId;
    private String productName; // ✅ 선택 필드 (내 리뷰 화면에서 쓰기 좋음)

    private Object authorId;    // Long을 그대로 실어도 되고, String으로 직렬화해도 OK
    private String authorName;

    private String content;
    private Integer rating;
    private Date createdAt;

    private Boolean mine;
}
