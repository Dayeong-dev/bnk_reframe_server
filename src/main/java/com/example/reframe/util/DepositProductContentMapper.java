package com.example.reframe.util;

import com.example.reframe.dto.DepositProductContentDTO;
import com.example.reframe.entity.DepositProductContent;

public class DepositProductContentMapper {
	public DepositProductContent toEntity(DepositProductContentDTO productContentDTO) {
		DepositProductContent productContent = new DepositProductContent();
		
		productContent.setContentId(productContentDTO.getContentId());
		productContent.setDepositProduct(productContentDTO.getDepositProduct());
		productContent.setContentOrder(productContentDTO.getContentOrder());
		productContent.setTitle(productContentDTO.getTitle());
		productContent.setContent(productContentDTO.getContent());
		productContent.setImageURL(productContentDTO.getImageURL());
		
		return productContent;
	}
	
	public DepositProductContentDTO toDTO(DepositProductContent productContent) {
		DepositProductContentDTO productContentDTO = new DepositProductContentDTO();
		
		productContentDTO.setContentId(productContent.getContentId());
		productContentDTO.setDepositProduct(productContent.getDepositProduct());
		productContentDTO.setContentOrder(productContent.getContentOrder());
		productContentDTO.setTitle(productContent.getTitle());
		productContentDTO.setContent(productContent.getContent());
		productContentDTO.setImageURL(productContent.getImageURL());
		
		return productContentDTO;
	}
}
