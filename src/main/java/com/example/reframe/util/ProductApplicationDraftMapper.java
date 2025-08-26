package com.example.reframe.util;

import com.example.reframe.dto.enroll.ProductApplicationDraftDTO;
import com.example.reframe.entity.enroll.ProductApplicationDraft;

public class ProductApplicationDraftMapper {
	
	private final DepositProductMapper productMapper = new DepositProductMapper();
	private final UserMapper userMapper = new UserMapper();
	

    public ProductApplicationDraft toEntity(ProductApplicationDraftDTO dto) {
        if (dto == null) return null;

        ProductApplicationDraft entity = new ProductApplicationDraft();
        entity.setId(dto.getId());
        entity.setUser(userMapper.toEntity(dto.getUser()));
        entity.setProduct(productMapper.toEntity(dto.getProduct()));
        entity.setStatus(dto.getStatus());
        entity.setFormData(dto.getFormData());
        return entity;
    }

    public ProductApplicationDraftDTO toDTO(ProductApplicationDraft entity) {
        if (entity == null) return null;

        ProductApplicationDraftDTO dto = new ProductApplicationDraftDTO();
        dto.setId(entity.getId());
        dto.setUser(userMapper.toDTO(entity.getUser()));
        dto.setProduct(productMapper.toDTO(entity.getProduct()));
        dto.setStatus(entity.getStatus());
        dto.setFormData(entity.getFormData());
        return dto;
    }
}