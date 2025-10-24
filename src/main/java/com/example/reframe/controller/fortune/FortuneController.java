package com.example.reframe.controller.fortune;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.reframe.dto.fortune.FortuneRequest;
import com.example.reframe.dto.fortune.FortuneResponse;
import com.example.reframe.service.fortune.FortuneService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/fortune")
@RequiredArgsConstructor
public class FortuneController {

    private final FortuneService fortuneService;

    @PostMapping
    public ResponseEntity<FortuneResponse> make(@RequestBody FortuneRequest req) {
        FortuneResponse res = fortuneService.generateFortune(req);
        return ResponseEntity.ok(res);
    }
}
