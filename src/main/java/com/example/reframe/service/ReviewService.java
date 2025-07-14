package com.example.reframe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reframe.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	public Map<String, Long> getReviewSentimentStats() {
	    List<Object[]> results = reviewRepository.findGroupedByNegative();
	    Map<String, Long> sentimentStats = new HashMap<>();

	    for (Object[] row : results) {
	        Boolean isNegative = (Boolean) row[0];
	        Long count = (Long) row[1];

	        if (isNegative != null && isNegative) {
	            sentimentStats.put("negative", count);
	        } else {
	            sentimentStats.put("positive", count);
	        }
	    }

	    return sentimentStats;
	}
	
	
}
