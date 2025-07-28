package com.example.reframe.dto;

import java.util.List;

import lombok.Data;

@Data
public class SearchResultResponse {
	private List<DepositProductDTO> deposits;
    private List<CardDto> cards;
}
