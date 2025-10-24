package com.example.reframe.dto.product;

import java.util.List;

import lombok.Data;

@Data
public class ProductStatusUpdateRequestDTO {

	private List<Long> ids;
    private String status;
}
