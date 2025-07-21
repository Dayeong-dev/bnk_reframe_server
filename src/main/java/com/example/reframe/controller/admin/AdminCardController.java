package com.example.reframe.controller.admin;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.reframe.dto.BenefitItem;
import com.example.reframe.dto.CardDto;
import com.example.reframe.dto.CardStatusUpdateRequestDTO;
import com.example.reframe.entity.Card;
import com.example.reframe.entity.Document;
import com.example.reframe.repository.CardRepository;
import com.example.reframe.service.CardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/admin/card")
public class AdminCardController {

	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private CardService cardService;

   
	
		// 종류 및 상태 이중 필터 따른 데이터 출력 
		@GetMapping("/filter")
		public List<CardDto> filterProducts(@RequestParam("categoryMajor") String categoryMajor,
		        							@RequestParam("status") String status) {
		    
			List<Card> result;

	        boolean isAllCategory = "all".equalsIgnoreCase(categoryMajor);
	        boolean isAllStatus = "all".equalsIgnoreCase(status);

	        if (isAllCategory && isAllStatus) {
	            result = cardRepository.findAll();
	        } else if (!isAllCategory && isAllStatus) {
	            result = cardRepository.findByCategoryMajor(categoryMajor);
	        } else if (isAllCategory) {
	            result = cardRepository.findByStatus(status);
	        } else {
	            result = cardRepository.findByCategoryMajorAndStatus(categoryMajor, status);
	        }

	        return result.stream()
	                .map(this::convertToDTO)
	                .collect(Collectors.toList());
	    }
		
		// 카드 디테일 데이터 반환 
		@GetMapping("/detail/{cardId}")
		public CardDto getProductDetail(@PathVariable("cardId") Long cardId) {
			Card card = cardRepository.findById(cardId)
					.orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다. ID: " + cardId));

			try {
				// JSON 데이터를 객체로 매핑
				ObjectMapper objectMapper = new ObjectMapper();
				
				List<BenefitItem> serviceList;
				
				if (card.getService() != null && !card.getService().isBlank() && !card.getService().equalsIgnoreCase("null")) {
					serviceList = objectMapper.readValue(
						card.getService(),
					    new TypeReference<List<BenefitItem>>(){}
					);
				} else {
					serviceList = Collections.emptyList(); // 또는 null 처리
				}

				CardDto result = convertToDTO(card);
				result.setServiceList(serviceList);

				return result;
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			
			return convertToDTO(card);
		}
		
		// 상세보기에서 수정 데이터 받기 
		@PutMapping("/update")
		public ResponseEntity<Void> updateCard(@RequestBody CardDto dto) {
		    Card card = cardRepository.findById(dto.getCardId())
		        .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않음"));
		      	    
			try {
			    List<BenefitItem> serviceList = dto.getServiceList();
			    
			    ObjectMapper objectMapper = new ObjectMapper();	
				String json = objectMapper.writeValueAsString(serviceList);
				
				dto.setService(json);	// JSON 형태로 저장

			    card.setName(dto.getName());
			    card.setDescription(dto.getDescription());
			    card.setTags(dto.getTags());
			    card.setCategoryMajor(dto.getCategoryMajor());
			    card.setStatus(dto.getStatus());
			    card.setAnnualFee(dto.getAnnualFee());
			    card.setService(dto.getService());
//			    card.setIssuedInfo(dto.getIssuedInfo());
			    card.setPointInfo(dto.getPointInfo());
//			    card.setOnlinePaymentGuide(dto.getOnlinePaymentGuide());
//			    card.setEtcGuide(dto.getEtcGuide());
			    card.setGuideInfo(dto.getGuideInfo());
		        card.setTerm(dto.getTermId() != null ? new Document(dto.getTermId()) : null);		// 추가
		        card.setManual(dto.getManualId() != null ? new Document(dto.getManualId()) : null);	// 추가
			    cardRepository.save(card);

			    System.out.println(card);
			    return ResponseEntity.ok().build();
			    
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		    
			return ResponseEntity.badRequest().build();
		}
		
		// 상품 등록 
		@PostMapping("/create")
		public ResponseEntity<Void> createCard(@RequestBody CardDto dto) {
		    
			try {
			    List<BenefitItem> serviceList = dto.getServiceList();
			    
			    ObjectMapper objectMapper = new ObjectMapper();	  	    
			    String json = objectMapper.writeValueAsString(serviceList);
			    
			    dto.setService(json);	// JSON 형태로 저장
				
			    Card card = Card.builder()
			        .name(dto.getName())
			        .description(dto.getDescription())
			        .tags(dto.getTags())
			        .categoryMajor(dto.getCategoryMajor())
			        .status(dto.getStatus())
			        .annualFee(dto.getAnnualFee())
			        .service(dto.getService())
//			        .issuedInfo(dto.getIssuedInfo())
			        .pointInfo(dto.getPointInfo())
//			        .onlinePaymentGuide(dto.getOnlinePaymentGuide())
//			        .etcGuide(dto.getEtcGuide())
			        .guideInfo(dto.getGuideInfo())
			        .term(dto.getTermId() != null ? new Document(dto.getTermId()) : null)		// 추가
			        .manual(dto.getManualId() != null ? new Document(dto.getManualId()) : null)	// 추가
			        .build();

			    cardRepository.save(card);
			    return ResponseEntity.ok().build();
			    
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			
			return ResponseEntity.badRequest().build();
		}
		
		// 상태 일괄 변경 요청 처리
		@PostMapping("/status-update")
		public ResponseEntity<Void> updateCardStatuses(@RequestBody CardStatusUpdateRequestDTO request) {
			cardService.updateStatuses(request.getIds(), request.getStatus());
		    return ResponseEntity.ok().build();
		}
		
		// DTO로 컨버전
		private CardDto convertToDTO(Card card) {
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
//	                .issuedInfo(card.getIssuedInfo())
	                .pointInfo(card.getPointInfo())
	                .viewCount(card.getViewCount())
	                .guideInfo(card.getGuideInfo())
//	                .onlinePaymentGuide(card.getOnlinePaymentGuide())
//	                .etcGuide(card.getEtcGuide())
		            .termId(card.getTerm() != null ? card.getTerm().getDocumentId() : null)		// 추가
		            .manualId(card.getManual() != null ? card.getManual().getDocumentId() : null)	// 추가
	                .subcategories(subcategories)
	                .build();
	    }
}
