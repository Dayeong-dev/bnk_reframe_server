package com.example.reframe.util;

import com.example.reframe.dto.CorporateUserDTO;
import com.example.reframe.entity.auth.CorporateUser;

public class CorpUserMapper {
	public CorporateUser toEntity(CorporateUserDTO corpUserDTO) {
		CorporateUser corpUser = new CorporateUser();
		
		corpUser.setUserId(corpUserDTO.getUserId());
		corpUser.setUser(corpUserDTO.getUser());
		corpUser.setBusinessNumber(corpUserDTO.getBusinessNumber());
		corpUser.setBusinessStartDate(corpUserDTO.getBusinessStartDate());
		corpUser.setCeoName(corpUserDTO.getCeoName());
		
		return corpUser;
	}
	
	public CorporateUserDTO toDTO(CorporateUser corpUser) {
		CorporateUserDTO corpUserDTO = new CorporateUserDTO();
		
		corpUserDTO.setUserId(corpUser.getUserId());
		corpUserDTO.setUser(corpUser.getUser());
		corpUserDTO.setBusinessNumber(corpUser.getBusinessNumber());
		corpUserDTO.setBusinessStartDate(corpUser.getBusinessStartDate());
		corpUserDTO.setCeoName(corpUser.getCeoName());
		
		return corpUserDTO;
	}
}
