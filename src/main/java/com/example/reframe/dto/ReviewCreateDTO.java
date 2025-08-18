package com.example.reframe.dto;
import lombok.*;

@Getter @Setter
public class ReviewCreateDTO {
    private Long productId;
    private String content;
    private Integer rating;
}