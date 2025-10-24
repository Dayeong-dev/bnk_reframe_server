package com.example.reframe.dto.enroll;

import com.example.reframe.dto.auth.UserDTO;
import com.example.reframe.dto.product.deposit.DepositProductDTO;
import com.example.reframe.enums.DraftStatus;

import lombok.Data;

@Data
public class ProductApplicationDraftDTO {
    private Long id;

    private UserDTO user;

    private DepositProductDTO product;

    private DraftStatus status; // IN_PROGRESS / SUBMITTED

    private String formData; // JSON 문자열
}
