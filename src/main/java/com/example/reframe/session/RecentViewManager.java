package com.example.reframe.session;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.reframe.dto.RecentViewDTO;

import jakarta.servlet.http.HttpSession;

@Component
public class RecentViewManager {
	private final HttpSession httpSession;
	
	public RecentViewManager(HttpSession httpSession) {
		this.httpSession = httpSession;
	}
	
	public void setProduct(String productType, Long id, String name) {
		// 최근 본 상품에 등록
		List<RecentViewDTO> recentViewList;
	    
	    if(httpSession.getAttribute("recentViewList") != null) {
	    	recentViewList = (List<RecentViewDTO>) httpSession.getAttribute("recentViewList");
	    } else {
	    	recentViewList = new ArrayList<>();
	    }
	    
	    RecentViewDTO recentViewDTO = new RecentViewDTO();
	    recentViewDTO.setType(productType);
	    recentViewDTO.setId(id);
	    recentViewDTO.setName(name);
	    
	    recentViewList.addFirst(recentViewDTO);
	    
	    httpSession.setAttribute("recentViewList", recentViewList);
	}
}
