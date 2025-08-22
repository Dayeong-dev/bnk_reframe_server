package com.example.reframe.controller.api.enroll;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reframe.dto.account.ProductAccountDetail;
import com.example.reframe.dto.enroll.EnrollForm;
import com.example.reframe.entity.ProductApplication;
import com.example.reframe.service.ProductApplicationService;

@RestController
@RequestMapping("/mobile/application")
public class ApplicationController {
	
	private final ProductApplicationService applicationService;
	
	public ApplicationController(ProductApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	@PostMapping("/add/{productId}")
	public ResponseEntity<String> addApplication(@PathVariable("productId") Long productId, @RequestBody EnrollForm enrollForm) {
		try {
			ProductApplication productApplication = applicationService.addApplication(productId, enrollForm);
			
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
	}
}
