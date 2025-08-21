package com.example.reframe.api.qna;

import com.example.reframe.entity.Qna;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class QnaApiResponse {
    private Integer qnaId;
    private String category;
    private String title;
    private String content;
    private String answer;
    private String status;
    private LocalDateTime regdate;
    private LocalDateTime moddate;

    public static QnaApiResponse from(Qna q) {
        return QnaApiResponse.builder()
                .qnaId(q.getQnaId())
                .category(q.getCategory())
                .title(q.getTitle())
                .content(q.getContent())
                .answer(q.getAnswer())
                .status(q.getStatus())
                .regdate(q.getRegdate())
                .moddate(q.getModDate())
                .build();
    }
}
