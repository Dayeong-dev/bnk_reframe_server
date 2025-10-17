package com.example.reframe.entity.deposit;

import java.time.LocalDate;

import com.example.reframe.entity.DepositProduct;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "deposit_product_rate", 
		uniqueConstraints = {
		        @UniqueConstraint(
		            name = "uk_product_month_range",
		            columnNames = {"product_id", "from_month", "to_month"})})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class DepositProductRate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	// 금리 ID
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
	private DepositProduct product;	// 예적금 상품 ID (deposit_product)
	
	@NotNull
	@Column(name = "from_month")
	private Integer fromMonth;		// 금리 적용 시작 개월
	
	@Column(name = "to_month")
	private Integer toMonth;		// 금리 적용 종료 개월
	
	@NotNull
	private Float rate;			// 적용 금리
	
	@NotNull
	@Column(name = "created_at")
	private LocalDate createdAt;	// 생성일시
}
