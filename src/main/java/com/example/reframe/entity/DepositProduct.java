package com.example.reframe.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.example.reframe.entity.deposit.PaymentCycle;
import com.example.reframe.entity.deposit.TermMode;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DEPOSIT_PRODUCT")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepositProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "CATEGORY", nullable = false, length = 20)
    private String category;

    @Column(name = "PURPOSE", length = 30) // 수정됨
    private String purpose;

    @Column(name = "SUMMARY", length = 500)
    private String summary;

    @Lob
    @Column(name = "DETAIL")
    private String detail;

    @Lob
    @Column(name = "MODAL_DETAIL")		// 추가됨
    private String modalDetail;
    
    @Lob
    @Column(name = "MODAL_RATE")		// 추가됨
    private String modalRate;
    
	@ManyToOne(fetch = FetchType.LAZY)	// 추가됨
	@JoinColumn(name = "TERM_ID")
	private Document term;
	
	@ManyToOne(fetch = FetchType.LAZY)	// 추가됨
	@JoinColumn(name = "MANUAL_ID")
	private Document manual;

    @Column(name = "MAX_RATE", precision = 5, scale = 2)
    private BigDecimal maxRate;

    @Column(name = "MIN_RATE", precision = 5, scale = 2)
    private BigDecimal minRate;

    @Column(name = "PERIOD")
    private Integer period;

    @Column(name = "STATUS", length = 1)
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Column(name = "VIEW_COUNT")
    private Long viewCount;

    @Column(name = "IMAGE_URL", nullable = true)
    private String imageUrl;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "PAYMENT_CYCLE", nullable = true)
    private PaymentCycle paymentCycle;
    
    @Column(name = "min_period_months")
    private Integer minPeriodMonths;
    
    @Column(name = "max_period_months")
    private Integer maxPeriodMonths;
    
    @Column(name = "term_list", nullable = true)
    private String termList;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "term_mode")
    private TermMode termMode;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

}
	