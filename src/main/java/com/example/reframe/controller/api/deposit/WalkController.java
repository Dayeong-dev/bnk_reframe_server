package com.example.reframe.controller.api.deposit;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reframe.service.WalkSyncService;
import com.example.reframe.service.WalkSyncService.Result;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mobile/walk")
@RequiredArgsConstructor
public class WalkController {

    private final WalkSyncService walkSyncService;

    @PostMapping("/sync/{appId}")
    public ResponseEntity<WalkSyncResponse> sync(
            @PathVariable("appId") Long appId,
            @RequestBody WalkSyncRequest req
    ) {
    	Result r = walkSyncService.sync(appId, req.stepsTodayTotal());

        return ResponseEntity.ok(new WalkSyncResponse(
                r.stepsThisMonth(),
                r.threshold(),
                r.confirmedThisMonth(),
                r.preferentialRate(),
                r.effectiveRate(), 
                r.todayStepsLastSynced(), 
                r.lastSyncDate()
        ));
    }
    
    @GetMapping("/summary/{appId}")
    public ResponseEntity<WalkSyncResponse> summary(@PathVariable("appId") Long appId) {
        Result r = walkSyncService.summary(appId);
        
        return ResponseEntity.ok(new WalkSyncResponse(
            r.stepsThisMonth(),
            r.threshold(),
            r.confirmedThisMonth(),
            r.preferentialRate(),
            r.effectiveRate(), 
            r.todayStepsLastSynced(), 
            r.lastSyncDate()
        ));
    }

    // --------- Request / Response DTO ---------

    // 오늘의 "누적 걸음수"만 보냅니다 (예: 8234)
    public record WalkSyncRequest(long stepsTodayTotal) {}

    // FE가 즉시 화면 갱신에 쓰는 값만 내려줍니다.
    public record WalkSyncResponse(
            long   stepsThisMonth,      // 이달 누적 걸음
            long   threshold,           // 임계치(10만/5만)
            boolean confirmedThisMonth, // 이달 우대 확정 여부
            BigDecimal preferentialRate,	// 우대금리(연) 합계
            BigDecimal effectiveRate,		// 최종 적용금리(연)
            long todaySteps,
            LocalDateTime lastSyncDate     
    ) {}
}