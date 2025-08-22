package com.example.reframe.util;

import com.example.reframe.dto.deposit.DepositProductRateDTO;
import com.example.reframe.entity.DepositProduct;
import com.example.reframe.entity.deposit.DepositProductRate;

public class DepositProductRateMapper {
	public DepositProductRate toEntity(DepositProductRateDTO dto) {
        if (dto == null) return null;

        DepositProductRate entity = new DepositProductRate();
        entity.setId(dto.getId());
        entity.setFromMonth(dto.getFromMonth());
        entity.setToMonth(dto.getToMonth());
        entity.setRate(dto.getRate());
        entity.setCreatedAt(dto.getCreatedAt());

        if (dto.getProductId() != null) {
            // 참조용 프록시 엔티티 (ID만 세팅)
            DepositProduct ref = new DepositProduct();
            ref.setProductId(dto.getProductId());
            entity.setProduct(ref);
        } else {
            entity.setProduct(null);
        }
        return entity;
    }

    public DepositProductRateDTO toDTO(DepositProductRate entity) {
        if (entity == null) return null;

        DepositProductRateDTO dto = new DepositProductRateDTO();
        dto.setId(entity.getId());
        dto.setFromMonth(entity.getFromMonth());
        dto.setToMonth(entity.getToMonth());
        dto.setRate(entity.getRate());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setProductId(
            entity.getProduct() != null ? entity.getProduct().getProductId() : null
        );
        return dto;
    }
}
