package com.example.reframe.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.reframe.dto.DepositProductDTO;
import com.example.reframe.dto.ProductStatusUpdateRequestDTO;
import com.example.reframe.entity.DepositProduct;
import com.example.reframe.entity.admin.ApprovalRequest;
import com.example.reframe.repository.ApprovalRequestRepository;
import com.example.reframe.repository.DepositProductRepository;
import com.example.reframe.service.ApprovalService;
import com.example.reframe.service.ProductService;
import com.example.reframe.util.SessionUtil;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin")
public class AdminTestController {

	@Autowired
	private DepositProductRepository productRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ApprovalService approvalService;

	@Autowired
	private ApprovalRequestRepository approvalRequestRepository;
	
	
	/*
	 * 이중 필터 사용 -> filterProducts() 메서드로 통합 
	 * 
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
	
	@GetMapping("/products/status/{status}")
	public List<DepositProductDTO> getProductsByStatus(@PathVariable("status")String status) {
		List<DepositProduct> products = productRepository.findByStatus(status);
		
		List<DepositProductDTO> dtoList = new ArrayList<>();
		for (DepositProduct product : products) {
			dtoList.add(convertToDTO(product));
		}
		return dtoList;
	}
	*/
	
	// 종류 및 상태 이중 필터 따른 데이터 출력 
	@GetMapping("/products/filter")
	public List<DepositProductDTO> filterProducts(@RequestParam("category") String category,
	        									  @RequestParam("status") String status) {
	    
	    List<DepositProduct> result;

	    if ("all".equals(category) && "all".equals(status)) {
	        result = productRepository.findAll();
	    } else if (!"all".equals(category) && "all".equals(status)) {
	        result = productRepository.findByCategory(category);
	    } else if ("all".equals(category)) {
	        result = productRepository.findByStatus(status);
	    } else {
	        result = productRepository.findByCategoryAndStatus(category, status);
	    }

	    return result.stream()
	                 .map(this::convertToDTO)
	                 .collect(Collectors.toList());
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
	

	// 전체일 경우 "/status" 단독 요청 처리
	@GetMapping("/products/status")
	public List<DepositProductDTO> getAllProducts() {
	    List<DepositProduct> products = productRepository.findAll();

	    List<DepositProductDTO> dtoList = new ArrayList<>();
	    for (DepositProduct product : products) {
	        dtoList.add(convertToDTO(product));
	    }
	    return dtoList;
	}
	
	
	// 상태 일괄 변경 요청 처리
	@PostMapping("/products/status-update")
	public ResponseEntity<Void> updateProductStatuses(@RequestBody ProductStatusUpdateRequestDTO request) {
	    productService.updateStatuses(request.getIds(), request.getStatus());
	    return ResponseEntity.ok().build();
	}
	
	// 상품 등록 
	@PostMapping("/products/create")
	public ResponseEntity<Void> createProduct(@RequestBody DepositProductDTO dto) {
	    DepositProduct product = DepositProduct.builder()
	        .name(dto.getName())
	        .category(dto.getCategory())
	        .purpose(dto.getPurpose())
	        .summary(dto.getSummary())
	        .detail(dto.getDetail())
	        .minRate(dto.getMinRate())
	        .maxRate(dto.getMaxRate())
	        .period(dto.getPeriod())
	        .status("S") // 기본값
	        .imageUrl(dto.getImageUrl())
	        .viewCount(0L)
	        .build();

	    productRepository.save(product);
	    return ResponseEntity.ok().build();
	}
	// 상세보기에서 수정 데이터 받기 
	@PutMapping("/products/update")
	public ResponseEntity<Void> updateProduct(@RequestBody DepositProductDTO dto,
			HttpSession session) {
		/*
		 * 결재 프로세스로 삭제
	    DepositProduct product = productRepository.findById(dto.getProductId())
	        .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않음"));
		
	    product.setName(dto.getName());	
	    product.setCategory(dto.getCategory());
	    product.setPurpose(dto.getPurpose());
	    product.setSummary(dto.getSummary());
	    product.setDetail(dto.getDetail());
	    product.setMinRate(dto.getMinRate());
	    product.setMaxRate(dto.getMaxRate());
	    product.setPeriod(dto.getPeriod());
	    product.setStatus(dto.getStatus());
		 */
		
		approvalService.requestApproval(dto, SessionUtil.getLoginUser(session).getUsername());
		
		return ResponseEntity.ok().build();
	}
	
	// 결재 승인
	@PostMapping("/approvals/{id}/approve")
	public ResponseEntity<Void> approveRequest(@PathVariable("id") Long id,
											  HttpSession session) {
	    approvalService.approveRequest(id, SessionUtil.getLoginUser(session).getUsername()); // TODO: 로그인 정보로 대체
	    return ResponseEntity.ok().build();
	}
	
	// 결재 반려
	@PostMapping("/approvals/{id}/reject")
	public ResponseEntity<Void> rejectRequest(@PathVariable("id") Long id,
											  @RequestBody Map<String, String> body,
											  HttpSession session) {
	    String reason = body.get("reason");
	    approvalService.rejectRequest(id, SessionUtil.getLoginUser(session).getUsername(), reason); // TODO: 로그인 정보로 대체
	    return ResponseEntity.ok().build();
	}
	
	// 결재 요청 목록 반환
	@GetMapping("/approvals")
	public List<ApprovalRequest> getAllPendingRequests() {
		List<ApprovalRequest> list = approvalRequestRepository.findByStatus("PENDING");
		return list ;
	}
	
	// 결재 요청 상세보기 
	@GetMapping("/approvals/{id}")
	public ApprovalRequest getApprovalDetail(@PathVariable("id")Long id) {
	    return approvalRequestRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("요청 없음"));
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
