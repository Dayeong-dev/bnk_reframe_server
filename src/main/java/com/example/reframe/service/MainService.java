package com.example.reframe.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reframe.dto.CardDto;
import com.example.reframe.dto.DepositProductDTO;
import com.example.reframe.dto.SearchResultResponse;

import jakarta.persistence.EntityManager;

@Service
public class MainService {
	
	private final DepositProductServiceImpl depositProductServiceImpl;
	private final CardService cardService;
	
	public MainService(DepositProductServiceImpl depositProductServiceImpl, 
					   CardService cardService, 
					   EntityManager em) {
		
		this.depositProductServiceImpl = depositProductServiceImpl;
		this.cardService = cardService;
	}
	
	@Transactional
	public SearchResultResponse searchByKeywords(String keywords) {
		SearchResultResponse searchList = new SearchResultResponse();
		
		List<DepositProductDTO> depositList = depositProductServiceImpl.searchByKeywords(keywords);
		List<CardDto> cardList = cardService.searchByKeywords(keywords);
		
		searchList.setDeposits(depositList);
		searchList.setCards(cardList);
		
		return searchList;
	}
}
