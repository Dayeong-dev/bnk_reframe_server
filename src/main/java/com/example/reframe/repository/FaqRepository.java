package com.example.reframe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.Faq;

public interface FaqRepository extends JpaRepository<Faq, Integer>{
	
	List<Faq> findAllByOrderByFaqIdAsc();

}
