package com.example.reframe.repository.customer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.reframe.entity.customer.Faq;

public interface FaqsRepository extends JpaRepository<Faq, Integer>, JpaSpecificationExecutor<Faq> {
     List<Faq> findAllByOrderByFaqIdAsc();
}
