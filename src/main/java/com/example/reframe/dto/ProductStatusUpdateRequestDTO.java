package com.example.reframe.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductStatusUpdateRequestDTO {

	private List<Long> ids;
    private String status;
}
