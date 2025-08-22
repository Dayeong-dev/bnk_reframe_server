package com.example.reframe.dto.account;

import com.example.reframe.dto.enroll.ProductApplicationDTO;
import com.example.reframe.dto.enroll.ProductApplicationInputDTO;

import lombok.Data;

@Data
public class ProductAccountDetail {
	private ProductApplicationDTO productApplication;
	private ProductApplicationInputDTO productApplicationInput;
}
