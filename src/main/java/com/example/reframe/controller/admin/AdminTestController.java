package com.example.reframe.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reframe.dto.DepositProductDTO;
import com.example.reframe.entity.DepositProduct;
import com.example.reframe.repository.DepositProductRepository;

@RestController
@RequestMapping("/admin")
public class AdminTestController {

	@Autowired
	private DepositProductRepository productRepository;
	
	
	// 모든 상품 데이터 반환 및 필터에 따른 데이터 반환 
	@GetMapping("/products/{value}")
	public List<DepositProductDTO> getProducts(@PathVariable("value") String value) {
        List<DepositProduct> products;

        if ("all".equals(value)) {
            products = productRepository.findByStatus("S"); 
        } else {
            products = productRepository.findByCategoryAndStatus(value, "S");
        }

        List<DepositProductDTO> dtoList = new ArrayList<>();

        for (DepositProduct product : products) {
            DepositProductDTO dto = convertToDTO(product);
            dtoList.add(dto);
        }

        return dtoList;
    }
	
	// 활성화 및 비활성화 버튼 누르고 상태 데이터 반환
	@PostMapping("/products/toggle")
	public ResponseEntity<Void> toggleProductActive(@RequestBody Map<String, Object> payload) {
//	    
//		List<Integer> ids = (List<Integer>) payload.get("ids");
//	    Boolean active = (Boolean) payload.get("active");
//

	    return ResponseEntity.ok().build();
	}
	
	// 상품 디테일 데이터 반환 
	@GetMapping("/products/detail/{productId}")
	public DepositProductDTO getProductDetail(@PathVariable("productId") Long productId) {
		DepositProduct product = productRepository.findById(productId)
				.orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다. ID: " + productId));

		return convertToDTO(product);
	}
	
	// DTO로 컨버전
	private DepositProductDTO convertToDTO(DepositProduct product) {
	    return DepositProductDTO.builder()
	            .productId(product.getProductId())
	            .name(product.getName())
	            .category(product.getCategory())
	            .purpose(product.getPurpose())
	            .summary(product.getSummary())
	            .detail(product.getDetail())
	            .maxRate(product.getMaxRate())
	            .minRate(product.getMinRate())
	            .period(product.getPeriod())
	            .status(product.getStatus())
	            .createdAt(product.getCreatedAt().toString())
	            .viewCount(product.getViewCount())
	            .imageUrl(product.getImageUrl())
	            .build();
	}
	
}
