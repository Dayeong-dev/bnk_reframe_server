package com.example.reframe.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "card")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long cardId; // 카드 고유 ID, 자동증가

    private String name; // 카드 이름

    private String description; // 카드 요약 설명

    private String tags; // 해시태그 (검색용)

    @Column(name = "category_major")
    private String categoryMajor; // 대분류 (개인(P)/기업(C)/체크(K)

    private String status; // 신청 가능 상태 (S/P/E)

    @Column(name = "annual_fee")
    private String annualFee; // 연회비

    @Lob
    private String service; //서비스

    @Lob
    @Column(name = "point_info")
    private String pointInfo; // 적립 정보 (JSON 등)

    @Column(name = "view_count")
    private Integer viewCount; // 조회수

    @Lob
    @Column(name = "guide_info")
    private String guideInfo; // 상품 안내 (관리자 등록)

    @Lob
    @Column(name = "online_payment_guide")
    private String onlinePaymentGuide; // 온라인 결제 안내 (관리자 등록)

    @Lob
    @Column(name = "etc_guide")
    private String etcGuide; // 기타 안내 (관리자 등록)

    @Lob
    @Column(name = "terms_guide")
    private String termsGuide; // 상품 설명서 및 이용약관
    
    
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CardCategoryRel> categoryRels = new HashSet<>(); // 카드-소분류 관계

    @PrePersist
    public void prePersist() {
        this.viewCount = this.viewCount == null ? 0 : this.viewCount;
    }
    

}
