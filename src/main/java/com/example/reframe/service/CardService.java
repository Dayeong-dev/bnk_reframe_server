package com.example.reframe.service;

import com.example.reframe.dto.CardDto;
import com.example.reframe.entity.Card;
import com.example.reframe.entity.DepositProduct;
import com.example.reframe.repository.CardRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    // 메인페이지: 조회수 상위 6개
    public List<CardDto> getTop6Cards() {
        return cardRepository.findTop6().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 상세페이지: 카드 상세정보 + 소분류명 리스트
    public CardDto getCardDetail(Long cardId) {
        Card card = cardRepository.findByIdWithCategories(cardId)
                .orElseThrow(() -> new RuntimeException("해당 카드 없음: " + cardId));
        return convertToDto(card);
    }

    // Entity → DTO 변환 메서드
    private CardDto convertToDto(Card card) {
        List<String> subcategories = card.getCategoryRels().stream()
                .map(rel -> rel.getSubcategory().getName())
                .collect(Collectors.toList());

        return CardDto.builder()
                .cardId(card.getCardId())
                .name(card.getName())
                .description(card.getDescription())
                .tags(card.getTags())
                .categoryMajor(card.getCategoryMajor())
                .status(card.getStatus())
                .annualFee(card.getAnnualFee())
                .service(card.getService())
                .issuedInfo(card.getIssuedInfo())
                .pointInfo(card.getPointInfo())
                .viewCount(card.getViewCount())
                .guideInfo(card.getGuideInfo())
                .onlinePaymentGuide(card.getOnlinePaymentGuide())
                .etcGuide(card.getEtcGuide())
                .termsGuide(card.getTermsGuide())
                .subcategories(subcategories)
                .build();
    }
    
    @Transactional
	public void updateStatuses(List<Long> ids, String status) {
	    for (Long id : ids) {
	        Card card = cardRepository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("상품 없음: " + id));
	        card.setStatus(status);
	    }
	}
}
