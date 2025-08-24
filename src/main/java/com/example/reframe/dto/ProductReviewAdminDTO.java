package com.example.reframe.dto;

public record ProductReviewAdminDTO(
	    Long id,
	    Long productId,
	    String productName,
	    String username,
	    Integer rating,
	    String content,
	    boolean negative,
	    Long createdAt // epoch millis
	) {}