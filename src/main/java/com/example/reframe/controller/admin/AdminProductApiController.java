package com.example.reframe.controller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reframe.dto.AdminProductListItemDto;
import com.example.reframe.service.AdminProductApplicationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/admin/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminProductApiController {
    private final AdminProductApplicationService service;

    @GetMapping
    public ResponseEntity<List<AdminProductListItemDto>> listProducts() {
        return ResponseEntity.ok(service.getProducts());
    }

    @GetMapping("/{productId}/applications")
    public ResponseEntity<List<Map<String, Object>>> applications(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(service.getApplications(productId));
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Long>> summary() {
        return ResponseEntity.ok(service.getSummary());
    }
}