package com.example.reframe.dto.fcm;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FcmMulticastRequestDto {
	private List<String> tokens;
    private String title;
    private String body;
}
