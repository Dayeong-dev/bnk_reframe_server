// src/main/java/com/example/reframe/dto/MyReviewDTO.java
package com.example.reframe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyReviewDTO {
    private Long id;
    private Long productId;
    private String productName;
    private String content;
    private Integer rating;
    private String createdAt; // ISO8601 문자열(프론트에서 파싱 쉬움)
}
