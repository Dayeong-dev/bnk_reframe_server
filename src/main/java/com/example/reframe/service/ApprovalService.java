package com.example.reframe.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reframe.dto.DepositProductDTO;
import com.example.reframe.entity.DepositProduct;
import com.example.reframe.entity.admin.ApprovalRequest;
import com.example.reframe.entity.admin.ApprovalRequestDetail;
import com.example.reframe.repository.ApprovalRequestDetailRepository;
import com.example.reframe.repository.ApprovalRequestRepository;
import com.example.reframe.repository.DepositProductRepository;

@Service
public class ApprovalService {

	@Autowired
	private ApprovalRequestRepository requestRepo;
	@Autowired
	private ApprovalRequestDetailRepository detailRepo;
	@Autowired
	private DepositProductRepository productRepo;

	public void requestApproval(DepositProductDTO dto,
								String requestedBy) {
		
		// 상품 조회
		DepositProduct product = productRepo.findById(dto.getProductId())
				.orElseThrow(() -> new RuntimeException("상품 없음"));
		
		ApprovalRequest request = ApprovalRequest.builder().productId(product.getProductId()).requestedBy(requestedBy)
				.requestedAt(LocalDateTime.now()).status("PENDING").build();

		List<ApprovalRequestDetail> details = new ArrayList<>();
		
		// 어떤 데이터가 수정된 것인지 확인
		if (!Objects.equals(product.getName(), dto.getName())) {
			details.add(makeDetail(request, "name", product.getName(), dto.getName()));
		}
		if (!Objects.equals(product.getCategory(), dto.getCategory())) {
			details.add(makeDetail(request, "category", product.getCategory(), dto.getCategory()));
		}
		if (!Objects.equals(product.getPurpose(), dto.getPurpose())) {
			details.add(makeDetail(request, "purpose", product.getPurpose(), dto.getPurpose()));
		}
		if (!Objects.equals(product.getSummary(), dto.getSummary())) {
			details.add(makeDetail(request, "summary", product.getSummary(), dto.getSummary()));
		}
		if (!Objects.equals(product.getDetail(), dto.getDetail())) {
			details.add(makeDetail(request, "detail", product.getDetail(), dto.getDetail()));
		}
		if (!Objects.equals(product.getMinRate(), dto.getMinRate())) {
			details.add(makeDetail(request, "minRate", String.valueOf(product.getMinRate()),
					String.valueOf(dto.getMinRate())));
		}
		if (!Objects.equals(product.getMaxRate(), dto.getMaxRate())) {
			details.add(makeDetail(request, "maxRate", String.valueOf(product.getMaxRate()),
					String.valueOf(dto.getMaxRate())));
		}
		if (!Objects.equals(product.getPeriod(), dto.getPeriod())) {
			details.add(makeDetail(request, "period", String.valueOf(product.getPeriod()),
					String.valueOf(dto.getPeriod())));
		}
		if (!Objects.equals(product.getStatus(), dto.getStatus())) {
			details.add(makeDetail(request, "status", product.getStatus(), dto.getStatus()));
		}

		request.setDetails(details);
		requestRepo.save(request); 
	}

	private ApprovalRequestDetail makeDetail(ApprovalRequest request,
										 	 String field,
										 	 String oldVal, 
											 String newVal) {
		return ApprovalRequestDetail.builder()
									.request(request)
									.fieldName(field)
									.oldValue(oldVal)
									.newValue(newVal)
									.build();
	}
	
	public void approveRequest(Long requestId, String approvedBy) {
        ApprovalRequest request = requestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("결재 요청이 존재하지 않음"));

        if (!"PENDING".equals(request.getStatus())) {
            throw new IllegalStateException("이미 처리된 요청입니다.");
        }

        DepositProduct product = productRepo.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("상품 없음"));

        for (ApprovalRequestDetail detail : request.getDetails()) {
            switch (detail.getFieldName()) {
                case "name" -> product.setName(detail.getNewValue());
                case "category" -> product.setCategory(detail.getNewValue());
                case "purpose" -> product.setPurpose(detail.getNewValue());
                case "summary" -> product.setSummary(detail.getNewValue());
                case "detail" -> product.setDetail(detail.getNewValue());
                case "minRate" -> product.setMinRate(new java.math.BigDecimal(detail.getNewValue()));
                case "maxRate" -> product.setMaxRate(new java.math.BigDecimal(detail.getNewValue()));
                case "period" -> product.setPeriod(Integer.parseInt(detail.getNewValue()));
                case "status" -> product.setStatus(detail.getNewValue());
            }
        }

        productRepo.save(product);

        request.setStatus("APPROVED");
        request.setApprovedBy(approvedBy);
        request.setApprovedAt(LocalDateTime.now());
        requestRepo.save(request);
    }

    public void rejectRequest(Long requestId, String approvedBy, String reason) {
        ApprovalRequest request = requestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("결재 요청이 존재하지 않음"));

        if (!"PENDING".equals(request.getStatus())) {
            throw new IllegalStateException("이미 처리된 요청입니다.");
        }

        request.setStatus("REJECTED");
        request.setApprovedBy(approvedBy);
        request.setApprovedAt(LocalDateTime.now());
        request.setRejectionReason(reason);
        requestRepo.save(request);
    }
	
	
	
}
