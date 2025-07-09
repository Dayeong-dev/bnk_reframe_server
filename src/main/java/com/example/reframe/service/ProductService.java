package com.example.reframe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reframe.entity.DepositProduct;
import com.example.reframe.repository.DepositProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

	@Autowired
	private DepositProductRepository productRepository;
	
	@Transactional
	public void updateStatuses(List<Long> ids, String status) {
	    for (Long id : ids) {
	        DepositProduct product = productRepository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("상품 없음: " + id));
	        product.setStatus(status);
	    }
	}
	
}
