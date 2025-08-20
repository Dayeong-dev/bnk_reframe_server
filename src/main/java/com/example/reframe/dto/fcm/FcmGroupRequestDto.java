package com.example.reframe.dto.fcm;

import lombok.Data;

@Data
public class FcmGroupRequestDto {
    private String groupCode;
    private String title;
    private String body;
}
