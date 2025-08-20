package com.example.reframe.controller.fcm;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reframe.dto.fcm.FcmBulkRequestDto;
import com.example.reframe.dto.fcm.FcmGroupRequestDto;
import com.example.reframe.dto.fcm.FcmSendDto;
import com.example.reframe.dto.fcm.FcmTokenDto;
import com.example.reframe.service.fcm.FcmService;
import com.example.reframe.service.fcm.FcmServiceIn;
import com.google.firebase.messaging.FirebaseMessagingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/fcm")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class FcmRestController {

    private final FcmServiceIn fcmServiceIn; 
    private final FcmService fcmService; 

    // 단일 발송 
    @PostMapping("/send")
    public ResponseEntity<Integer> pushMessage(@RequestBody @Validated FcmSendDto fcmSendDto) throws IOException {
        int result = fcmServiceIn.sendMessageTo(fcmSendDto);
        return ResponseEntity.ok(result);
    }

    // 토큰 등록 
    @PostMapping("/register")
    public ResponseEntity<String> registerToken(@RequestBody FcmTokenDto dto) {
        fcmService.registerToken(dto);
        return ResponseEntity.ok("토큰 등록 완료");
    }

    // 그룹 발송 (리턴: 성공 건수)
    @PostMapping("/send-group")
    public ResponseEntity<Map<String, Object>> sendFcmToGroup(@RequestBody @Validated FcmGroupRequestDto dto)
            throws FirebaseMessagingException {
        int success = fcmService.sendToGroup(dto.getGroupCode(), dto.getTitle(), dto.getBody());
        return ResponseEntity.ok(Map.of(
                "result", "OK",
                "target", dto.getGroupCode(),
                "success", success
        ));
    }

    // 전체 발송( 리턴: 성공 건수)
    @PostMapping("/send-all")
    public ResponseEntity<Map<String, Object>> sendFcmToAll(@RequestBody @Validated FcmGroupRequestDto dto)
            throws FirebaseMessagingException {
        int success = fcmService.sendToAll(dto.getTitle(), dto.getBody());
        return ResponseEntity.ok(Map.of(
                "result", "OK",
                "target", "all",
                "success", success
        ));
    }
    
    
    
 // 여러 그룹 발송 (컨트롤러에서만 그룹 리스트를 처리)
    @PostMapping("/send-bulk")
    public ResponseEntity<Map<String, Object>> sendBulk(@RequestBody @Validated FcmBulkRequestDto dto)
            throws FirebaseMessagingException {

        // 'all'이 포함되어 오면 전체 발송으로 전환
        if (dto.getGroupCodes().stream().anyMatch(gc -> "all".equalsIgnoreCase(gc))) {
            int success = fcmService.sendToAll(dto.getTitle(), dto.getBody());
            return ResponseEntity.ok(Map.of(
                    "result", "OK",
                    "targets", List.of("all"),
                    "success", success
            ));
        }
        System.out.println("......test");
        int totalSuccess = 0;
        for (String gc : dto.getGroupCodes()) {
            totalSuccess += fcmService.sendToGroup(gc, dto.getTitle(), dto.getBody());
        }

        return ResponseEntity.ok(Map.of(
                "result", "OK",
                "targets", dto.getGroupCodes(),
                "success", totalSuccess
        ));
    }
    
    @GetMapping("/ping")
    public String ping() { return "pong"; }
}