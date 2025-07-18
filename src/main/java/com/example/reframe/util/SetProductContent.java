package com.example.reframe.util;

import java.util.List;

import com.example.reframe.dto.DepositProductContentDTO;

public class SetProductContent {
	
	public String setDepositDetail(List<DepositProductContentDTO> productContentList) {
		
		StringBuilder sb = new StringBuilder();
		
		for(DepositProductContentDTO productContent : productContentList) {
			String title = String.format("<h2>%s</h2>", productContent.getTitle().replace("\\n", "<br/>"));
			String content = String.format("<p>%s</p>", productContent.getContent().replace("\\n", "<br/>"));
			String image = String.format("<img src='%s' alt='' />", productContent.getImageURL());

			sb.append("<section>" + title + content + image + "</section>");	
		}
		
		return sb.toString();
	}
}
