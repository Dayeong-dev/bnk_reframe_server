package com.example.reframe.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reframe.dto.CardApprovalRequestDTO;
import com.example.reframe.dto.CardApprovalRequestDetailDTO;
import com.example.reframe.dto.CardDto;
import com.example.reframe.entity.Card;
import com.example.reframe.entity.Document;
import com.example.reframe.entity.admin.CardApprovalRequest;
import com.example.reframe.entity.admin.CardApprovalRequestDetail;
import com.example.reframe.repository.CardApprovalRequestRepository;
import com.example.reframe.repository.CardRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CardApprovalService {

	@Autowired
    private CardRepository cardRepo;

    @Autowired
    private CardApprovalRequestRepository requestRepo;

    // 카드 수정 결재 요청
    public void requestApproval(CardDto dto, String requestedBy) {
        Card card = cardRepo.findById(dto.getCardId())
                .orElseThrow(() -> new EntityNotFoundException("카드 없음"));

        CardApprovalRequest request = CardApprovalRequest.builder()
                .card(card)
                .requestedBy(requestedBy)
                .requestedAt(LocalDateTime.now())
                .status("PENDING")
                .build();
        
//        System.out.println(card);

        List<CardApprovalRequestDetail> details = Stream.of(
                compareField("name", card.getName(), dto.getName(), request),
                compareField("description", card.getDescription(), dto.getDescription(), request),
                compareField("tags", card.getTags(), dto.getTags(), request),
                compareField("categoryMajor", card.getCategoryMajor(), dto.getCategoryMajor(), request),
                compareField("status", card.getStatus(), dto.getStatus(), request),
                compareField("annualFee", card.getAnnualFee(), dto.getAnnualFee(), request),
                compareField("service", card.getService(), dto.getService(), request),
                compareField("pointInfo", card.getPointInfo(), dto.getPointInfo(), request),
                compareField("guideInfo", card.getGuideInfo(), dto.getGuideInfo(), request),
//                compareField("onlinePaymentGuide", card.getOnlinePaymentGuide(), dto.getOnlinePaymentGuide(), request),
//                compareField("etcGuide", card.getEtcGuide(), dto.getEtcGuide(), request),
                compareField("term", card.getTerm() != null ? card.getTerm().getDocumentId().toString() : null, dto.getTermId() != null ? dto.getTermId().toString() : null, request), 
                compareField("manual", card.getManual() != null ? card.getManual().getDocumentId().toString() : null, dto.getManualId() != null ? dto.getManualId().toString() : null, request)
        ).filter(Objects::nonNull).collect(Collectors.toList());

        request.setDetails(details);
        requestRepo.save(request);  // cascade 설정에 따라 detail도 저장됨
    }

    private CardApprovalRequestDetail compareField(String fieldName, String oldVal, String newVal, CardApprovalRequest request) {
        String oldStr = oldVal == null ? "" : oldVal.trim();
        String newStr = newVal == null ? "" : newVal.trim();

        if (!oldStr.equals(newStr)) {
            return CardApprovalRequestDetail.builder()
                    .request(request)
                    .fieldName(fieldName)
                    .oldValue(oldStr)
                    .newValue(newStr)
                    .build();
        }

        return null;
    }

    // 결재 승인
    public void approveRequest(Long requestId, String approvedBy) {
        CardApprovalRequest request = requestRepo.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("결재 요청 없음"));

        if (!"PENDING".equals(request.getStatus())) {
            throw new IllegalStateException("이미 처리된 요청입니다.");
        }

        Card card = request.getCard();
        for (CardApprovalRequestDetail detail : request.getDetails()) {
            switch (detail.getFieldName()) {
                case "name" -> card.setName(detail.getNewValue());
                case "description" -> card.setDescription(detail.getNewValue());
                case "tags" -> card.setTags(detail.getNewValue());
                case "categoryMajor" -> card.setCategoryMajor(detail.getNewValue());
                case "status" -> card.setStatus(detail.getNewValue());
                case "annualFee" -> card.setAnnualFee(detail.getNewValue());
                case "service" -> card.setService(detail.getNewValue());
                case "pointInfo" -> card.setPointInfo(detail.getNewValue());
                case "guideInfo" -> card.setGuideInfo(detail.getNewValue());
//                case "onlinePaymentGuide" -> card.setOnlinePaymentGuide(detail.getNewValue());
//                case "etcGuide" -> card.setEtcGuide(detail.getNewValue());
                case "term" -> card.setTerm(detail.getNewValue() != null ? new Document(Integer.parseInt(detail.getNewValue())) : null);
                case "manual" -> card.setManual(detail.getNewValue() != null ? new Document(Integer.parseInt(detail.getNewValue())) : null);	
            }
        }

        request.setStatus("APPROVED");
        request.setApprovedBy(approvedBy);
        request.setApprovedAt(LocalDateTime.now());

        cardRepo.save(card);
        requestRepo.save(request);
    }

    // 결재 반려
    public void rejectRequest(Long requestId, String approvedBy, String reason) {
        CardApprovalRequest request = requestRepo.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("결재 요청 없음"));

        if (!"PENDING".equals(request.getStatus())) {
            throw new IllegalStateException("이미 처리된 요청입니다.");
        }

        request.setStatus("REJECTED");
        request.setApprovedBy(approvedBy);
        request.setApprovedAt(LocalDateTime.now());
        request.setRejectionReason(reason);

        requestRepo.save(request);
    }

    // 결재 요청 전체 조회
    public List<CardApprovalRequestDTO> getPendingRequestDtos() {
        return requestRepo.findByStatus("PENDING")
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 단건 상세 조회
    public CardApprovalRequestDTO getRequestDetailDto(Long id) {
        CardApprovalRequest entity = requestRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("결재 요청 없음"));
        return convertToDTO(entity);
    }

    private CardApprovalRequestDTO convertToDTO(CardApprovalRequest entity) {
        return CardApprovalRequestDTO.builder()
                .id(entity.getId())
                .cardId(entity.getCard().getCardId())
                .cardName(entity.getCard().getName())
                .requestedBy(entity.getRequestedBy())
                .approvedBy(entity.getApprovedBy())
                .requestedAt(entity.getRequestedAt())
                .approvedAt(entity.getApprovedAt())
                .status(entity.getStatus())
                .rejectionReason(entity.getRejectionReason())
                .details(
                        entity.getDetails().stream()
                                .map(d -> CardApprovalRequestDetailDTO.builder()
                                        .fieldName(d.getFieldName())
                                        .oldValue(d.getOldValue())
                                        .newValue(d.getNewValue())
                                        .build())
                                .collect(Collectors.toList())
                )
                .build();
    }

    public List<CardApprovalRequest> getMyCardRequests(String username) {
        return requestRepo.findByRequestedBy(username);
    }

    public List<CardApprovalRequest> getMyCardRequestsByStatus(String username, String status) {
        return requestRepo.findByRequestedByAndStatus(username, status);
    }

}
