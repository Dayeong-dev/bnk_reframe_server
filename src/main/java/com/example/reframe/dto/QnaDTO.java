// src/main/java/com/example/reframe/dto/QnaDTO.java
package com.example.reframe.dto;

import java.time.LocalDateTime;
import com.example.reframe.entity.Qna;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QnaDTO {

    private Integer qnaId;
    private Long userId;
    private String username;      // 👈 추가
    private String category;
    private String title;
    private String content;
    private String answer;
    private String status;
    private LocalDateTime regdate;
    private LocalDateTime moddate;

    public QnaDTO(Qna qna) {
        this.qnaId   = qna.getQnaId();
        this.userId  = qna.getUser() != null ? qna.getUser().getId() : null;
        this.username= qna.getUser() != null ? qna.getUser().getUsername() : null; // 👈 추가
        this.category= qna.getCategory();
        this.title   = qna.getTitle();
        this.content = qna.getContent();
        this.answer  = qna.getAnswer();
        this.status  = qna.getStatus();
        this.regdate = qna.getRegdate();
        this.moddate = qna.getModDate();
    }
}
