package com.example.reframe.controller.admin;

import com.example.reframe.dto.ProductApplicationsResponseDTO;
import com.example.reframe.service.AdminProductApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/product-applications")
public class AdminProductApplicationController {

    private final AdminProductApplicationService service;

    @GetMapping("/{productId}")
    public ResponseEntity<ProductApplicationsResponseDTO> get(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(service.getApplications(productId));
    }
}