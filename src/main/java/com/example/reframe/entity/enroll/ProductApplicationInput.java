package com.example.reframe.entity.enroll;

import com.example.reframe.entity.DepositProduct;
import com.example.reframe.entity.ProductApplication;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "product_application_input")
@Data
public class ProductApplicationInput {
	
	@Id
    @Column(name = "application_id")
    private Long applicationId;		// 상품 ID

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    @MapsId
    private ProductApplication application;
	
    @Column(name = "biz_map1", nullable = true)
	private String bizMap1;		// 납입 기간 
	
    @Column(name = "biz_map2", nullable = true)
	private String bizMap2;		// 납입 금액
	
    @Column(name = "biz_map3", nullable = true)
	private String bizMap3;		// 이체일
	
    @Column(name = "biz_map4", nullable = true)
	private String bizMap4;		// 시작일
	
    @Column(name = "biz_map5", nullable = true)
	private String bizMap5;		// 만기 처리 방식
	
    @Column(name = "biz_map6", nullable = true)
	private String bizMap6;		// 비과세 여부
	
    @Column(name = "biz_map7", nullable = true)
	private String bizMap7;		// 모임명
	
    @Column(name = "biz_map8", nullable = true)
	private String bizMap8;		// 모임 구분
}
