package com.example.reframe.controller.api.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.reframe.dto.auth.RealnameVerificationDTO;
import com.example.reframe.service.auth.RealnameVerificationService;
import com.example.reframe.service.auth.VerifyStatus;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mobile/verification")
@RequiredArgsConstructor
public class RealnameVerificationController {

    private final RealnameVerificationService realnameVerificationService;

    @GetMapping("/status")
    public ResponseEntity<RealnameVerificationDTO> status() {
    	RealnameVerificationDTO rv = realnameVerificationService.checkVerification();
        
    	return ResponseEntity.ok(rv);
    }
    
    @PostMapping("/request-code")
    public ResponseEntity<String> requestCode(@RequestBody RealnameVerificationDTO rv) {
        return ResponseEntity.ok(realnameVerificationService.requestCode(rv));
    }

    @PostMapping("/verify-code")
    public ResponseEntity<VerifyStatus> verifyCode(@RequestBody RealnameVerificationDTO rv, @RequestParam("inputCode") String inputCode) {
        return ResponseEntity.ok(realnameVerificationService.verifyCode(rv, inputCode));
    }
}