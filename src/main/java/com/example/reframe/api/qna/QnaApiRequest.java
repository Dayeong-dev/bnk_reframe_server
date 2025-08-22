package com.example.reframe.api.qna;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QnaApiRequest {
    @NotBlank
    private String category;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
