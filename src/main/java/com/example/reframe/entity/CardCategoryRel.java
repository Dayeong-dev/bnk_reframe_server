package com.example.reframe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
	    name = "card_category_rel"
	)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardCategoryRel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relId; // 인조 PK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card; // 카드 FK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategory_id")
    private CardSubcategory subcategory; // 소분류 FK
}
