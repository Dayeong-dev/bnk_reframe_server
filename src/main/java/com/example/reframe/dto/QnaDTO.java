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
    private String username; // user 엔티티에서 username만 가져옴
    private String category;
    private String title;
    private String content;
    private String answer;
    private String status;
    private LocalDateTime regdate;
    private LocalDateTime moddate;

    // Qna 엔티티로부터 DTO로 변환
    public QnaDTO(Qna qna) {
        this.qnaId = qna.getQnaId();
        this.username = qna.getUser() != null ? qna.getUser().getUsername() : null;
        this.category = qna.getCategory();
        this.title = qna.getTitle();
        this.content = qna.getContent();
        this.answer = qna.getAnswer();
        this.status = qna.getStatus();
        this.regdate = qna.getRegdate();
        this.moddate = qna.getModDate();
    }
}
