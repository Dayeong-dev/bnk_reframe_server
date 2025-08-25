package com.example.reframe.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reframe.auth.CurrentUser;
import com.example.reframe.entity.DepositProduct;
import com.example.reframe.entity.auth.User;
import com.example.reframe.entity.enroll.DraftStatus;
import com.example.reframe.entity.enroll.ProductApplicationDraft;
import com.example.reframe.repository.enroll.ProductApplicationDraftRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductApplicationDraftService {

	private final ProductApplicationDraftRepository applicationDraftRepository;
	private final CurrentUser currentUser;
	
	@Transactional(readOnly = true)
	public String getFormData(Long productId) {		// 저장된 폼 데이터 조회
		Long uid = currentUser.id();
        if (uid == null) throw new IllegalStateException("로그인이 필요합니다.");
        
        Optional<ProductApplicationDraft> optApp = 
        		applicationDraftRepository.findByProduct_ProductId(productId);
        
        if(optApp.isEmpty()) return null;
        
        return optApp.get().getFormData();
	}
	
	@Transactional
    public void upsertDraft(Long productId, String formDataJson) {	// 입력 중 이탈 시 저장 및 수정
		Long uid = currentUser.id();
        if (uid == null) throw new IllegalStateException("로그인이 필요합니다.");
        
        User user = new User();
        user.setId(uid);
        
        DepositProduct product = new DepositProduct();
		product.setProductId(productId);
        
        ProductApplicationDraft draft = applicationDraftRepository
                .findByUser_IdAndProduct_ProductIdAndStatus(uid, productId, DraftStatus.IN_PROGRESS)
		        .orElseGet(() -> ProductApplicationDraft.builder()
		                .user(user)
		                .product(product)
		                .status(DraftStatus.IN_PROGRESS)
		                .build());
        
        draft.setFormData(formDataJson);

        applicationDraftRepository.save(draft); // 새로 생성 or 기존 갱신
    }
	
	@Transactional
	public void markSubmitted(Long productId) {		// 작성 완료 후 Status 변경
		Long uid = currentUser.id();
        if (uid == null) throw new IllegalStateException("로그인이 필요합니다.");
        
        Optional<ProductApplicationDraft> optDraft = applicationDraftRepository
        		.findByUser_IdAndProduct_ProductIdAndStatus(uid, productId, DraftStatus.IN_PROGRESS);
	
        
        if(optDraft.isEmpty()) {
        	return;
        }
        
        ProductApplicationDraft applicationDraft = optDraft.get();
        
        applicationDraft.setStatus(DraftStatus.SUBMITTED);
        applicationDraftRepository.save(applicationDraft);
	}
}
