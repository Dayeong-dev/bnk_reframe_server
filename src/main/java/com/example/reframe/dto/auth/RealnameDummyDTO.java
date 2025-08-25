package com.example.reframe.dto.auth;

import lombok.Data;

@Data
public class RealnameDummyDTO {
	private Long id;

    private String name;

    private String rrnFront;

    private String phoneNumber;

    private String carrier;

    private String verificationCode;

    private String ci;
}
