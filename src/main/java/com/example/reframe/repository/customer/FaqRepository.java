package com.example.reframe.repository.customer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.customer.Faq;

public interface FaqRepository extends JpaRepository<Faq, Integer>{
	
	List<Faq> findAllByOrderByFaqIdAsc();

}
