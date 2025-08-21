package com.example.reframe.entity.deposit;

import java.time.LocalDate;

import com.example.reframe.entity.DepositProduct;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "product_input_format")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ProductInputFormat {
	@Id
    @Column(name = "product_id")
    private Long productId;		// 상품 ID

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @MapsId
    private DepositProduct product;

    @Column(name = "input_1", nullable = false)
    private Integer input1;		// 납입 기간 입력 여부
    
    @Column(name = "input_2", nullable = false)
    private Integer input2;		// 납입/가입 금액 입력 여부
    
    @Column(name = "input_3", nullable = false)
    private Integer input3;		// 이체일 입력 여부
    
    @Column(name = "input_4", nullable = false)
    private Integer input4;		// 시작일 입력 여부
    
    @Column(name = "input_5", nullable = false)
    private Integer input5;		// 만기 처리 방식 입력 여부
    
    @Column(name = "input_6", nullable = false)
    private Integer input6;		// 비과세 여부 입력 여부
    
    @Column(name = "input_7", nullable = false)
    private Integer input7;		// 모임명 입력 여부
    
    @Column(name = "input_8", nullable = false)
    private Integer input8;		// 모임구분 입력 여부
    
    @Column(name = "from_account_req", nullable = false)
    private Integer fromAccountReq;		// 출금계좌 입력 여부
    
    @Column(name = "maturity_account_req", nullable = false)
    private Integer maturityAccountReq;		// 만기 시 입금계좌 필요 여부
}
