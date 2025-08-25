package com.example.reframe.util;

import com.example.reframe.dto.auth.RealnameVerificationDTO;
import com.example.reframe.entity.auth.RealnameVerification;

public class RealnameVerificationMapper {
	
	private final UserMapper userMapper = new UserMapper();

    public RealnameVerification toEntity(RealnameVerificationDTO dto) {
        if (dto == null) return null;

        RealnameVerification entity = new RealnameVerification();
        entity.setId(dto.getId());
        entity.setUser(userMapper.toEntity(dto.getUser()));
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setCarrier(dto.getCarrier());
        entity.setRrnFront(dto.getRrnFront());
        entity.setGender(dto.getGender());
        entity.setCi(dto.getCi());
        entity.setVerifiedAt(dto.getVerifiedAt());
        entity.setExpiresAt(dto.getExpiresAt());

        return entity;
    }

    public RealnameVerificationDTO toDTO(RealnameVerification entity) {
        if (entity == null) return null;

        RealnameVerificationDTO dto = new RealnameVerificationDTO();
        dto.setId(entity.getId());
        dto.setUser(userMapper.toDTO(entity.getUser()));
        dto.setName(entity.getName());
        dto.setPhone(entity.getPhone());
        dto.setCarrier(entity.getCarrier());
        dto.setRrnFront(entity.getRrnFront());
        dto.setGender(entity.getGender());
        dto.setCi(entity.getCi());
        dto.setVerifiedAt(entity.getVerifiedAt());
        dto.setExpiresAt(entity.getExpiresAt());

        return dto;
    }
}
