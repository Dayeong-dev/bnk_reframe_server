package com.example.reframe.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reframe.dto.ProductViewDTO;
import com.example.reframe.entity.DepositProduct;
import com.example.reframe.repository.DepositProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

	@Autowired
	private DepositProductRepository depositProductRepository;
	
	@Transactional
	public void updateStatuses(List<Long> ids, String status) {
	    for (Long id : ids) {
	        DepositProduct product = depositProductRepository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("상품 없음: " + id));
	        product.setStatus(status);
	    }
	}

	public List<ProductViewDTO> getTopViewedProducts() {
	    List<DepositProduct> products = depositProductRepository.findTop10ByOrderByViewCountDesc();

	    List<ProductViewDTO> dtoList = new ArrayList<>();
	    for (DepositProduct p : products) {
	        dtoList.add(new ProductViewDTO(p.getName(), p.getViewCount()));
	    }
	    return dtoList;
	}
	
	
	
	
	
}
