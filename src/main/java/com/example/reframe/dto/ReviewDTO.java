package com.example.reframe.dto;

import com.example.reframe.entity.User;

import lombok.Data;

@Data
public class ReviewDTO {
    private String content;
    private String userId;  // 또는 userId만 받아도 됨
}
