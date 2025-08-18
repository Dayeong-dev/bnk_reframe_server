package com.example.reframe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.ProductReview;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long>{
	List<ProductReview> findByProduct_ProductIdOrderByIdDesc(Long productId);

}
