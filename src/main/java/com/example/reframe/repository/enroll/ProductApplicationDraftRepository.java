package com.example.reframe.repository.enroll;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.enroll.DraftStatus;
import com.example.reframe.entity.enroll.ProductApplicationDraft;

public interface ProductApplicationDraftRepository extends JpaRepository<ProductApplicationDraft, Long> {

	Optional<ProductApplicationDraft> findByProduct_ProductId(Long productId);

	Optional<ProductApplicationDraft> findByUser_IdAndProduct_ProductIdAndStatus(Long uid, Long productId,
			DraftStatus inProgress);

}
