package com.example.reframe.repository.review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.reframe.entity.customer.Faq;
import com.example.reframe.entity.review.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer>{

	@Query("SELECT r.negative, COUNT(r) FROM Review r GROUP BY r.negative")
	List<Object[]> findGroupedByNegative();
	

}
