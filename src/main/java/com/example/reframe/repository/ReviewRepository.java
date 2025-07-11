package com.example.reframe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.Faq;
import com.example.reframe.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer>{
	

}
