package com.example.reframe.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reframe.dto.BenefitItem;
import com.example.reframe.dto.CardDto;
import com.example.reframe.entity.Card;
import com.example.reframe.entity.CardCategoryRel;
import com.example.reframe.entity.CardSubcategory;
import com.example.reframe.entity.CardTestResult;
import com.example.reframe.repository.CardRepository;
import com.example.reframe.repository.CardSubcategoryRepository;
import com.example.reframe.repository.CardTestResultRepository;
import com.example.reframe.util.MarkdownUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Service
public class CardService {

	private final ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private CardSubcategoryRepository cardSubcategoryRepository;

	@Autowired
	private CardTestResultRepository cardTestResultRepository;
	
	@Autowired
	private DocumentService documentService; 

	@Autowired
	private EntityManager em;

	// 메인 - 조회수 상위 6개 카드 가져오기
	public List<CardDto> getTop6Cards() {
		return cardRepository.findTop6().stream().map(this::convertToDto).collect(Collectors.toList());
	}

	// 메인 - 슬라이더: 전체 카드 가져오기
	public List<CardDto> findAllCards() {
		List<Card> cards = cardRepository.findAll();
		return cards.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	// 상세 - 카드 상세정보 가져오기 + 소분류명 리스트
	@Transactional
	public CardDto getCardDetail(Long cardId) {
		Card card = cardRepository.findByIdWithCategories(cardId)
				.orElseThrow(() -> new RuntimeException("해당 카드 없음: " + cardId));
		
		card.setAnnualFee(card.getAnnualFee().replace("\\n", "<br>"));
		card.setGuideInfo(MarkdownUtil.toHtml(card.getGuideInfo()));	// MarkDown → HTML
		
		List<String> termImages = new ArrayList<>();
        List<String> manualImages = new ArrayList<>();
        
        if(card.getTerm() != null) {
        	termImages = documentService.getImages(card.getTerm().getDocumentId());	// 약관 이미지 조회
        }
        if(card.getManual() != null) {
        	manualImages = documentService.getImages(card.getManual().getDocumentId());	// 상품설명서 이미지 조회
        }
        
        CardDto cardDTO = convertToDto(card);
        
        cardDTO.setTermImages(termImages);		// 약관 이미지 파일 set
        cardDTO.setManualImages(manualImages);	// 상품설명서 이미지 파일 set
		
		return cardDTO;
	}

	// 상세 - 조회수 +1 증가
	public void incrementViewCount(Long cardId) {
		Card card = cardRepository.findById(cardId).orElseThrow(() -> new RuntimeException("카드가 없습니다."));
		card.setViewCount(card.getViewCount() + 1);
		cardRepository.save(card);
	}

	// 카드 목록 - Entity → DTO 변환 메서드
	private CardDto convertToDto(Card card) {
		List<BenefitItem> serviceList = null;
		try {
		    if (card.getService() != null && !card.getService().isBlank()) {
		        serviceList = objectMapper.readValue(card.getService(), new TypeReference<List<BenefitItem>>() {});
		    }
		} catch (Exception e) {
		    System.err.println("SERVICE 파싱 실패 - cardId: " + card.getCardId());
		    System.err.println("JSON: " + card.getService());
		    e.printStackTrace(); // 오류 로그 전체 출력
		}
		
		return CardDto.builder().cardId(card.getCardId()).name(card.getName())
				.description(card.getDescription().replace("\\n", "<br>")).tags(card.getTags())
				.categoryMajor(card.getCategoryMajor()).status(card.getStatus()).annualFee(card.getAnnualFee())
//				.service(card.getService().replace("\\n", "<br>")).pointInfo(card.getPointInfo().replace("\\n", "<br>"))
				.viewCount(card.getViewCount()).guideInfo(card.getGuideInfo())
//				.onlinePaymentGuide(card.getOnlinePaymentGuide()).etcGuide(card.getEtcGuide())
	            .termId(card.getTerm() != null ? card.getTerm().getDocumentId() : null)		// 추가
	            .manualId(card.getManual() != null ? card.getManual().getDocumentId() : null)	// 추가
				.serviceList(card.getServiceList()).build();
	}

	@Transactional
	public void updateStatuses(List<Long> ids, String status) {
		for (Long id : ids) {
			Card card = cardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("상품 없음: " + id));
			card.setStatus(status);
		}
	}

	// 목록 - 대분류/소분류/키워드로 카드 검색
	public List<CardDto> getCards(String categoryMajor, String subcategory, String keyword) {
		List<Card> cards = cardRepository.findByDynamicCondition(categoryMajor, subcategory, keyword);
		return cards.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	// 비교함 - 선택한 카드 리스트 반환
	public List<CardDto> getCardsForCompare(List<Long> ids) {
		List<Card> cards = cardRepository.findAllById(ids);

		// 카드 ID별 DTO 맵 생성
		Map<Long, CardDto> cardMap = cards.stream().map(this::convertToDto)
				.collect(Collectors.toMap(CardDto::getCardId, Function.identity()));

		// 원래 ID 리스트 순서대로 DTO 리스트 생성
		List<CardDto> sortedCards = ids.stream().map(cardMap::get).filter(Objects::nonNull)
				.collect(Collectors.toList());

		return sortedCards;
	}

	// 목록 - 전체 소분류명 리스트 가져오기
	public List<String> getAllSubcategories() {
		return cardSubcategoryRepository.findAll().stream().map(CardSubcategory::getName).collect(Collectors.toList());
	}

	// 목록 - 태그 기반으로 소분류 연결 후 저장
	@Transactional
	public void saveCard(Card card) {
		String[] tags = card.getTags().split("#");
		for (String tag : tags) {
			String name = tag.trim();
			if (name.isEmpty())
				continue;

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


	@Transactional
	public List<CardDto> searchByKeywords(String keywords) {
		String[] words = keywords.split(" ");
		StringBuilder sql = new StringBuilder("SELECT * FROM card WHERE 1=1 ");

		for (int i = 0; i < words.length; i++) {
			sql.append("AND (name LIKE :word").append(i).append(" OR description LIKE :word").append(i)
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
