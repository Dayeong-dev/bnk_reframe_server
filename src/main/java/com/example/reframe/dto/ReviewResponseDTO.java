package com.example.reframe.dto;
import java.util.Date;
import lombok.*;

@Getter @Setter @Builder
public class ReviewResponseDTO {
    private Long id;
    private Long productId;
    private String content;
    private Integer rating;
    private String authorName;
    private Date createdAt;
}