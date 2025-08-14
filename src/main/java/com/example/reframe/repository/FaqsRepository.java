package com.example.reframe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.reframe.entity.Faq;

public interface FaqsRepository extends JpaRepository<Faq, Integer>, JpaSpecificationExecutor<Faq> {
     List<Faq> findAllByOrderByFaqIdAsc();
}
