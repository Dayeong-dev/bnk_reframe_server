// src/main/java/com/example/reframe/dto/ReviewUpdateDTO.java
package com.example.reframe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewUpdateDTO {
    private String content;   // null이면 기존 유지
    private Integer rating;   // null이면 기존 유지
}
