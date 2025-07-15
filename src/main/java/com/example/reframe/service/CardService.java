package com.example.reframe.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reframe.dto.CardDto;
import com.example.reframe.dto.DepositProductDTO;
import com.example.reframe.entity.Card;
import com.example.reframe.entity.DepositProduct;
import com.example.reframe.repository.CardRepository;


import com.example.reframe.entity.CardCategoryRel;
import com.example.reframe.entity.CardSubcategory;
import com.example.reframe.entity.CardTestResult;
import com.example.reframe.repository.CardSubcategoryRepository;
import com.example.reframe.repository.CardTestResultRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardSubcategoryRepository cardSubcategoryRepository;

    @Autowired
    private CardTestResultRepository cardTestResultRepository;
    
    @Autowired
    private EntityManager em;
    
    // 메인 - 조회수 상위 6개 카드 가져오기
    public List<CardDto> getTop6Cards() {
        return cardRepository.findTop6()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 상세 - 카드 상세정보 가져오기 + 소분류명 리스트
    public CardDto getCardDetail(Long cardId) {
        Card card = cardRepository.findByIdWithCategories(cardId)
                .orElseThrow(() -> new RuntimeException("해당 카드 없음: " + cardId));
        System.out.println(cardId);
        return convertToDto(card);
    }

    // 상세 - 조회수 +1 증가
    public void incrementViewCount(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("카드가 없습니다."));
        card.setViewCount(card.getViewCount() + 1);
        cardRepository.save(card);
    }


    // 카드 목록 -  Entity → DTO 변환 메서드
    private CardDto convertToDto(Card card) {
        return CardDto.builder()
                .cardId(card.getCardId())
                .name(card.getName())
                .description(card.getDescription())
                .tags(card.getTags())
                .categoryMajor(card.getCategoryMajor())
                .status(card.getStatus())
                .annualFee(card.getAnnualFee())
                .service(card.getService().replace("\\n", "<br>"))
                .pointInfo(card.getPointInfo().replace("\\n", "<br>"))
                .viewCount(card.getViewCount())
                .guideInfo(card.getGuideInfo())
                .onlinePaymentGuide(card.getOnlinePaymentGuide())
                .etcGuide(card.getEtcGuide())
                .termsGuide(card.getTermsGuide())
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

    // 목록 -  대분류/소분류/키워드로 카드 검색 + 페이징
    public Page<CardDto> getCards(String categoryMajor, String subcategory, String keyword, Pageable pageable) {
        Page<Card> cards = cardRepository.findByDynamicCondition(categoryMajor, subcategory, keyword, pageable);
        return cards.map(this::convertToDto);
    }

    // 비교함 -  선택한 카드 리스트 반환
    public List<CardDto> getCardsForCompare(List<Long> ids) {
        return cardRepository.findAllById(ids)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 목록 -  전체 소분류명 리스트 가져오기
    public List<String> getAllSubcategories() {
        return cardSubcategoryRepository.findAll()
                .stream()
                .map(CardSubcategory::getName)
                .collect(Collectors.toList());
    }

    // 목록 -  태그 기반으로 소분류 연결 후 저장
    @Transactional
    public void saveCard(Card card) {
        String[] tags = card.getTags().split("#");
        for (String tag : tags) {
            String name = tag.trim();
            if (name.isEmpty()) continue;

            Optional<CardSubcategory> subcategoryOpt = cardSubcategoryRepository.findByName(name);
            subcategoryOpt.ifPresent(subcategory -> {
                CardCategoryRel rel = new CardCategoryRel();
                rel.setCard(card);
                rel.setSubcategory(subcategory);
                card.getCategoryRels().add(rel);
            });
        }
        cardRepository.save(card); // Cascade로 REL도 같이 저장
    }
    
    @Transactional
    public void saveOrUpdateTestResult(String resultType) {
        CardTestResult cardTestResult = cardTestResultRepository.findByResultType(resultType)
            .orElseGet(() -> new CardTestResult(resultType));

        cardTestResult.incrementCount();
        cardTestResultRepository.save(cardTestResult);
    }

    public List<CardDto> getRecommendedCardsByResult(String resultType) {
        // 예시: 결과유형에 따라 카드 아이디 고정
        List<Long> cardIds;

        switch (resultType) {
            case "ONLINE-SAVE-PLAN":
                cardIds = List.of(1L, 2L, 3L); // 결과유형에 맞는 카드 ID
                break;
            case "OFFLINE-SPEND-IMPULSE":
                cardIds = List.of(4L, 5L, 6L);
                break;
            default:
                cardIds = List.of(); // fallback
        }

        return cardRepository.findAllById(cardIds)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<CardDto> getTopFiveByViewCount() {
    	List<Card> cardList = cardRepository.findTopFiveByViewCount();
    	
    	return cardList.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    
    @Transactional
    public List<CardDto> searchByKeywords(String keywords) {
        String[] words = keywords.split(" ");
        StringBuilder sql = new StringBuilder("SELECT * FROM card WHERE 1=1 ");

        for (int i = 0; i < words.length; i++) {
            sql.append("AND (name LIKE :word").append(i)
               .append(" OR description LIKE :word").append(i)
               .append(" OR service LIKE :word").append(i).append(") ");
        }

        Query query = em.createNativeQuery(sql.toString(), Card.class);
        for (int i = 0; i < words.length; i++) {
            query.setParameter("word" + i, "%" + words[i] + "%");
        }
        
        @SuppressWarnings("unchecked")
		List<Card> result = query.getResultList();

        return result.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}
