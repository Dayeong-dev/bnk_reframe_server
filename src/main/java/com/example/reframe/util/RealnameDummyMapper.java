package com.example.reframe.util;

import com.example.reframe.dto.auth.RealnameDummyDTO;
import com.example.reframe.entity.auth.RealnameDummy;

public class RealnameDummyMapper {

    public RealnameDummy toEntity(RealnameDummyDTO dto) {
        if (dto == null) return null;

        RealnameDummy entity = new RealnameDummy();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setRrnFront(dto.getRrnFront());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setCarrier(dto.getCarrier());
        entity.setVerificationCode(dto.getVerificationCode());
        entity.setCi(dto.getCi());

        return entity;
    }

    public RealnameDummyDTO toDTO(RealnameDummy entity) {
        if (entity == null) return null;

        RealnameDummyDTO dto = new RealnameDummyDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setRrnFront(entity.getRrnFront());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setCarrier(entity.getCarrier());
        dto.setVerificationCode(entity.getVerificationCode());
        dto.setCi(entity.getCi());

        return dto;
    }
}