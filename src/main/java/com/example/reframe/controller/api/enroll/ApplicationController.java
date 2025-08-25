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
import com.example.reframe.service.ProductApplicationDraftService;
import com.example.reframe.service.ProductApplicationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mobile/application")
@RequiredArgsConstructor
public class ApplicationController {
	
	private final ProductApplicationService applicationService;
	private final ProductApplicationDraftService applicationDraftService;

	
	@PostMapping("/add/{productId}")
	public ResponseEntity<String> addApplication(@PathVariable("productId") Long productId, @RequestBody EnrollForm enrollForm) {
		try {
			ProductApplication productApplication = applicationService.addApplication(productId, enrollForm);
			
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
	}
	
	@GetMapping("/draft/{productId}")
	public ResponseEntity<JsonNode> getDraft(@PathVariable("productId") Long productId) {
		String formJson = applicationDraftService.getFormData(productId);
		
		if(formJson == null) {
			return ResponseEntity.noContent().build();
		}
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(formJson);
			
			return ResponseEntity.ok().body(node);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@PostMapping("/draft/{productId}")
    public ResponseEntity<Void> upsertDraft(@PathVariable("productId") Long productId, @RequestBody EnrollForm enrollForm) {	
		try {
			ObjectMapper mapper = new ObjectMapper();
			String formData = mapper.writeValueAsString(enrollForm);
			
			applicationDraftService.upsertDraft(productId, formData);
		
	        return ResponseEntity.ok().build();
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		
    }
	
	@PostMapping("/submit/{productId}")
    public ResponseEntity<Void> markSubmitted(@PathVariable("productId") Long productId) {
		
		applicationDraftService.markSubmitted(productId);
		
        return ResponseEntity.ok().build();
    }
}
