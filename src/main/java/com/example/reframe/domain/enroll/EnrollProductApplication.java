package com.example.reframe.domain.enroll;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "PRODUCT_APPLICATION")
public class EnrollProductApplication {

    @Id
    @Column(name = "ID")
    // 조회만 쓰면 @GeneratedValue 불필요. (INSERT 있으면 시퀀스 설정)
    private Long id;

    @Column(name = "CLOSE_AT")   private OffsetDateTime closeAt;
    @Column(name = "START_AT")   private OffsetDateTime startAt;
    @Column(name = "STATUS")     private String status;
    @Column(name = "FROM_ACCOUNT_ID")     private Long fromAccountId;
    @Column(name = "MATURITY_ACCOUNT_ID") private Long maturityAccountId;

    @Column(name = "PRODUCT_ID", nullable = false)        private Long productId;
    @Column(name = "PRODUCT_ACCOUNT_ID", nullable = false) private Long productAccountId;
    @Column(name = "USER_ID", nullable = false)           private Long userId;

    @Column(name = "BASE_RATE_AT_ENROLL")          private Double baseRateAtEnroll;
    @Column(name = "TERM_MONTHS_AT_ENROLL")        private Long termMonthsAtEnroll;
    @Column(name = "EFFECTIVE_RATE_ANNUAL")        private Double effectiveRateAnnual;
    @Column(name = "PREFERENTIAL_RATE_ANNUAL")     private Double preferentialRateAnnual;
    @Column(name = "WALK_THRESHOLD_STEPS")         private Long walkThresholdSteps;

    // getter/setter
    public Long getId() { return id; }
    public Long getProductId() { return productId; }
    public Long getUserId() { return userId; }
    // 필요시 나머지 getter 추가
    public void setId(Long id) { this.id = id; }
    public void setProductId(Long productId) { this.productId = productId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
