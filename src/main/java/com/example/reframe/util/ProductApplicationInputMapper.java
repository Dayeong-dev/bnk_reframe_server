package com.example.reframe.util;

import com.example.reframe.dto.enroll.ProductApplicationInputDTO;
import com.example.reframe.entity.ProductApplication;
import com.example.reframe.entity.enroll.ProductApplicationInput;

public class ProductApplicationInputMapper {

	public ProductApplicationInput toEntity(ProductApplicationInputDTO dto) {
        if (dto == null) return null;

        ProductApplicationInput entity = new ProductApplicationInput();

        Long appId = dto.getApplicationId();
        entity.setApplicationId(appId); // @MapsId와 일치하도록 id 세팅

        if (appId != null) {
            // 프록시 참조(로딩 없이 연관관계만 설정)
            ProductApplication ref = new ProductApplication();
            ref.setId(appId);
            entity.setApplication(ref); // @MapsId: 연관 엔티티의 id와 동일해야 함
        } else {
            entity.setApplication(null);
        }

        entity.setBizMap1(dto.getBizMap1());
        entity.setBizMap2(dto.getBizMap2());
        entity.setBizMap3(dto.getBizMap3());
        entity.setBizMap4(dto.getBizMap4());
        entity.setBizMap5(dto.getBizMap5());
        entity.setBizMap6(dto.getBizMap6());
        entity.setBizMap7(dto.getBizMap7());
        entity.setBizMap8(dto.getBizMap8());
        return entity;
    }

    public ProductApplicationInputDTO toDTO(ProductApplicationInput entity) {
        if (entity == null) return null;

        ProductApplicationInputDTO dto = new ProductApplicationInputDTO();

        // @MapsId로 인해 applicationId가 채워져 있을 가능성이 큼.
        // 방어적으로 연관 엔티티에서 한 번 더 보완
        Long appId = entity.getApplicationId();
        if (appId == null && entity.getApplication() != null) {
            appId = entity.getApplication().getId();
        }
        dto.setApplicationId(appId);

        dto.setBizMap1(entity.getBizMap1());
        dto.setBizMap2(entity.getBizMap2());
        dto.setBizMap3(entity.getBizMap3());
        dto.setBizMap4(entity.getBizMap4());
        dto.setBizMap5(entity.getBizMap5());
        dto.setBizMap6(entity.getBizMap6());
        dto.setBizMap7(entity.getBizMap7());
        dto.setBizMap8(entity.getBizMap8());
        return dto;
    }
}
