package com.example.reframe.util;

import java.util.Collections;
import java.util.List;

import com.example.reframe.dto.DepositProductContentDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SetProductContent {
	
	public String setDepositDetail(String json) throws JsonMappingException, JsonProcessingException {
		
		StringBuilder sb = new StringBuilder();
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		List<DepositProductContentDTO> productContentList;
		
		if (json != null && !json.equals("")) {
			productContentList = objectMapper.readValue(
				json,
			    new TypeReference<List<DepositProductContentDTO>>(){}
			);
		} else {
			productContentList = Collections.emptyList(); // 또는 null 처리
		}	 
		
		for(DepositProductContentDTO productContent : productContentList) {
			String title = String.format("<h2>%s</h2>", productContent.getTitle().replace("\n", "<br/>").replace("\\n", "<br/>"));
			String content = String.format("<p>%s</p>", productContent.getContent().replace("\n", "<br/>").replace("\\n", "<br/>"));
			String image = String.format("<img src='%s' alt='' />", productContent.getImageURL());

			sb.append("<section>" + title + content + image + "</section>");	
		}
		
		return sb.toString();
	}
}
