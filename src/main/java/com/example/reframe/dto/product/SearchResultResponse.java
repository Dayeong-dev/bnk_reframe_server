package com.example.reframe.dto.product;

import java.util.List;

import com.example.reframe.dto.product.card.CardDto;
import com.example.reframe.dto.product.deposit.DepositProductDTO;

import lombok.Data;

@Data
public class SearchResultResponse {
	private List<DepositProductDTO> deposits;
    private List<CardDto> cards;
}
