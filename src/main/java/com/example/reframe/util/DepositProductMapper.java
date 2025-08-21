package com.example.reframe.util;

import java.text.SimpleDateFormat;

import com.example.reframe.dto.DepositProductDTO;
import com.example.reframe.entity.DepositProduct;

public class DepositProductMapper {
	private DocumentMapper documentMapper = new DocumentMapper();
	
	public DepositProduct toEntity(DepositProductDTO depositProductDTO) {
		if(depositProductDTO == null) return null;
		
		DepositProduct depositProduct = new DepositProduct();
		
		depositProduct.setProductId(depositProductDTO.getProductId());
		depositProduct.setName(depositProductDTO.getName());
		depositProduct.setCategory(depositProductDTO.getCategory());
		depositProduct.setPurpose(depositProductDTO.getPurpose());
		depositProduct.setSummary(depositProductDTO.getSummary());
		depositProduct.setDetail(depositProductDTO.getDetail());
		depositProduct.setModalDetail(depositProductDTO.getModalDetail());
		depositProduct.setModalRate(depositProductDTO.getModalRate());
		depositProduct.setTerm(documentMapper.toEntity(depositProductDTO.getTerm()));
		depositProduct.setManual(documentMapper.toEntity(depositProductDTO.getManual()));
		depositProduct.setMinRate(depositProductDTO.getMinRate());
		depositProduct.setMaxRate(depositProductDTO.getMaxRate());
		depositProduct.setPeriod(depositProductDTO.getPeriod());
		depositProduct.setStatus(depositProductDTO.getStatus());
//		depositProduct.setCreatedAt(depositProductDTO.getCreatedAt());
		depositProduct.setViewCount(depositProductDTO.getViewCount());
		depositProduct.setImageUrl(depositProductDTO.getImageUrl());
		depositProduct.setPaymentCycle(depositProductDTO.getPaymentCycle());
		depositProduct.setMinPeriodMonths(depositProductDTO.getMinPeriodMonths());
		depositProduct.setMaxPeriodMonths(depositProductDTO.getMaxPeriodMonths());
		depositProduct.setTermList(depositProductDTO.getTermList());
		depositProduct.setTermMode(depositProductDTO.getTermMode());
	
		return depositProduct;
	}
	
	public DepositProductDTO toDTO(DepositProduct depositProduct) {
		if(depositProduct == null) return null;
		
		DepositProductDTO depositProductDTO = new DepositProductDTO();
		
		depositProductDTO.setProductId(depositProduct.getProductId());
		depositProductDTO.setName(depositProduct.getName());
		depositProductDTO.setCategory(depositProduct.getCategory());
		depositProductDTO.setPurpose(depositProduct.getPurpose());
		depositProductDTO.setSummary(depositProduct.getSummary());
		depositProductDTO.setDetail(depositProduct.getDetail());
		depositProductDTO.setModalDetail(depositProduct.getModalDetail());
		depositProductDTO.setModalRate(depositProduct.getModalRate());
		depositProductDTO.setTerm(documentMapper.toDTO(depositProduct.getTerm()));
		depositProductDTO.setManual(documentMapper.toDTO(depositProduct.getManual()));
		depositProductDTO.setMinRate(depositProduct.getMinRate());
		depositProductDTO.setMaxRate(depositProduct.getMaxRate());
		depositProductDTO.setPeriod(depositProduct.getPeriod());
		depositProductDTO.setStatus(depositProduct.getStatus());
		depositProductDTO.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd").format(depositProduct.getCreatedAt()));
		depositProductDTO.setViewCount(depositProduct.getViewCount());
		depositProductDTO.setImageUrl(depositProduct.getImageUrl());
		depositProductDTO.setPaymentCycle(depositProduct.getPaymentCycle());
		depositProductDTO.setMinPeriodMonths(depositProduct.getMinPeriodMonths());
		depositProductDTO.setMaxPeriodMonths(depositProduct.getMaxPeriodMonths());
		depositProductDTO.setTermList(depositProduct.getTermList());
		depositProductDTO.setTermMode(depositProduct.getTermMode());
	
		return depositProductDTO;
	}
}
