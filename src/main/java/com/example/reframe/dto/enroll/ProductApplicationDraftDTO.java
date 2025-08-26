package com.example.reframe.dto.enroll;

import com.example.reframe.dto.DepositProductDTO;
import com.example.reframe.dto.auth.UserDTO;
import com.example.reframe.entity.enroll.DraftStatus;

import lombok.Data;

@Data
public class ProductApplicationDraftDTO {
    private Long id;

    private UserDTO user;

    private DepositProductDTO product;

    private DraftStatus status; // IN_PROGRESS / SUBMITTED

    private String formData; // JSON 문자열
}
