package com.example.reframe.util;

import com.example.reframe.dto.enroll.ProductApplicationDTO;
import com.example.reframe.entity.ProductApplication;

public class ProductApplicationMapper {
	
	private UserMapper userMapper = new UserMapper();
	private DepositProductMapper depositProductMapper = new DepositProductMapper();
	private AccountMapper accountMapper = new AccountMapper();

	public ProductApplication toEntity(ProductApplicationDTO applicationDTO) {
		if(applicationDTO == null) return null;
		
		ProductApplication application = new ProductApplication();
		
		application.setProduct(depositProductMapper.toEntity(applicationDTO.getProduct()));
		application.setUser(userMapper.toEntity(applicationDTO.getUser()));
		application.setProductAccount(accountMapper.toEntity(applicationDTO.getProductAccount()));
		application.setFromAccount(accountMapper.toEntity(applicationDTO.getFromAccount()));
		application.setMaturityAccount(accountMapper.toEntity(applicationDTO.getMaturityAccount()));
		
		application.setStatus(applicationDTO.getStatus());
		application.setStartAt(applicationDTO.getStartAt());
		application.setCloseAt(applicationDTO.getCloseAt());
		
		return application;
	}
	
	public ProductApplicationDTO toDTO(ProductApplication application) {
		if(application == null) return null;
		
		ProductApplicationDTO applicationDTO = new ProductApplicationDTO();
		
		applicationDTO.setProduct(depositProductMapper.toDTO(application.getProduct()));
		applicationDTO.setUser(userMapper.toDTO(application.getUser()));
		applicationDTO.setProductAccount(accountMapper.toDTO(application.getProductAccount()));
		applicationDTO.setFromAccount(accountMapper.toDTO(application.getFromAccount()));
		applicationDTO.setMaturityAccount(accountMapper.toDTO(application.getMaturityAccount()));
		
		applicationDTO.setStatus(application.getStatus());
		applicationDTO.setStartAt(application.getStartAt());
		applicationDTO.setCloseAt(application.getCloseAt());
		
		return applicationDTO;
	}
}
