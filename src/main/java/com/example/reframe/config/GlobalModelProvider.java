package com.example.reframe.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.reframe.dto.RecentViewDTO;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class GlobalModelProvider {

    @ModelAttribute("recentViewList")
    public Set<RecentViewDTO> recentViewSet(HttpSession session) {
    	Set<RecentViewDTO> set = new LinkedHashSet<>();
    	
    	if(session.getAttribute("recentViewList") != null) {
    		List<RecentViewDTO> list = (List<RecentViewDTO>) session.getAttribute("recentViewList");
    		
    		for(RecentViewDTO recentViewDTO : list) {
    			set.add(recentViewDTO);
    		}		
    	}
    	
        return set;
    }
}