package com.example.reframe.util;

import com.example.reframe.dto.deposit.ProductInputFormatDTO;
import com.example.reframe.entity.deposit.ProductInputFormat;

public class ProductInputFormatMapper {

	public ProductInputFormat toEntity(ProductInputFormatDTO inputFormatDTO) {
		ProductInputFormat inputFormat = new ProductInputFormat();
		
		inputFormat.setProductId(inputFormatDTO.getProductId());
		inputFormat.setInput1(inputFormatDTO.getInput1());
		inputFormat.setInput2(inputFormatDTO.getInput2());
		inputFormat.setInput3(inputFormatDTO.getInput3());
		inputFormat.setInput4(inputFormatDTO.getInput4());
		inputFormat.setInput5(inputFormatDTO.getInput5());
		inputFormat.setInput6(inputFormatDTO.getInput6());
		inputFormat.setInput7(inputFormatDTO.getInput7());
		inputFormat.setInput8(inputFormatDTO.getInput8());
		inputFormat.setFromAccountReq(inputFormatDTO.getFromAccountReq());
		inputFormat.setMaturityAccountReq(inputFormatDTO.getMaturityAccountReq());
		
		return inputFormat;
	}
	
	public ProductInputFormatDTO toDTO(ProductInputFormat inputFormat) {
		ProductInputFormatDTO inputFormatDTO = new ProductInputFormatDTO();
		
		inputFormatDTO.setProductId(inputFormat.getProductId());
		inputFormatDTO.setInput1(inputFormat.getInput1());
		inputFormatDTO.setInput2(inputFormat.getInput2());
		inputFormatDTO.setInput3(inputFormat.getInput3());
		inputFormatDTO.setInput4(inputFormat.getInput4());
		inputFormatDTO.setInput5(inputFormat.getInput5());
		inputFormatDTO.setInput6(inputFormat.getInput6());
		inputFormatDTO.setInput7(inputFormat.getInput7());
		inputFormatDTO.setInput8(inputFormat.getInput8());
		inputFormatDTO.setFromAccountReq(inputFormat.getFromAccountReq());
		inputFormatDTO.setMaturityAccountReq(inputFormat.getMaturityAccountReq());
		
		return inputFormatDTO;
	}
}
