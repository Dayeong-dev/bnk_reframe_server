package com.example.reframe.service.fcm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.reframe.dto.fcm.FcmTokenDto;
import com.example.reframe.entity.fcm.FcmHistory;
import com.example.reframe.entity.fcm.FcmHistoryDetail;
import com.example.reframe.entity.fcm.FcmToken;
import com.example.reframe.repository.fcm.FcmHistoryDetailRepository;
import com.example.reframe.repository.fcm.FcmHistoryRepository;
import com.example.reframe.repository.fcm.FcmTokenRepository;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmService {

    private final FcmTokenRepository fcmTokenRepository;
    private final FcmHistoryRepository fcmHistoryRepository;
    private final FcmHistoryDetailRepository fcmHistoryDetailRepository;

    // 토큰 DB 저장
    @Transactional
    public void registerToken(FcmTokenDto dto) {
        String raw = dto.getToken();
        String trimmed = (raw == null) ? null : raw.trim();
        dto.setToken(trimmed);

        log.info("👉 [registerToken] userId={} | token={}", dto.getUserId(), mask(trimmed));

        fcmTokenRepository.findByToken(dto.getToken()).ifPresentOrElse(
            existing -> {
                log.info("✅ 기존 토큰 발견 → userId 업데이트: {} (token={})", dto.getUserId(), mask(trimmed));
                existing.setUserId(dto.getUserId());
                fcmTokenRepository.save(existing);
            },
            () -> {
                log.info("🆕 신규 토큰 저장: {}", mask(trimmed));
                FcmToken newToken = FcmToken.builder()
                        .userId(dto.getUserId())
                        .token(dto.getToken())
                        .build();
                fcmTokenRepository.save(newToken);
            }
        );
    }

    /**
     * 전체 사용자 발송 ("all")
     * @return 성공 건수
     */
    @Transactional
    public int sendToAll(String title, String body) throws FirebaseMessagingException {
        List<FcmToken> targets = fcmTokenRepository.findAll();
        log.info("🚀 [sendToAll] total targets = {}", targets.size());
        if (targets.isEmpty()) {
            log.warn("⚠️ 전체 발송 대상 토큰 없음");
            return 0;
        }
        return sendAndLog(targets, "all", title, body);
    }


    /**
     * 그룹 코드로 발송
     * @return 성공 건수
     */
    @Transactional
    public int sendToGroup(String groupCode, String title, String body) throws FirebaseMessagingException {
        log.info("🚀 [sendToGroup] groupCode={}", groupCode);
        List<FcmToken> targets = fcmTokenRepository.findByGroupCode(groupCode);
        log.info("🎯 target size = {}", targets.size());
        if (targets.isEmpty()) {
            log.warn("⚠️ 전송 대상 토큰 없음 (groupCode={})", groupCode);
            return 0;
        }
        return sendAndLog(targets, groupCode, title, body);
    }

    /**
     * 공통: 멀티캐스트 1회 전송 + 이력 저장 (토큰 100개 이하 가정)
     */
    private int sendAndLog(List<FcmToken> targetEntities, String targetGroup, String title, String body)
            throws FirebaseMessagingException {

        // 1) 이력 헤더 저장 (@PrePersist로 createdAt 자동)
        FcmHistory history = FcmHistory.builder()
                .title((title == null || title.isBlank()) ? "제목 없음" : title)
                .body((body == null || body.isBlank()) ? "내용 없음" : body)
                .targetGroup(targetGroup)               
                .sentCount(targetEntities.size())
                .build();
        fcmHistoryRepository.save(history);

        // 2) 멀티캐스트 메시지 구성 (요청 토큰 순서 = 엔티티 순서 유지)
        List<String> tokens = targetEntities.stream().map(FcmToken::getToken).toList();

        MulticastMessage message = MulticastMessage.builder()
                .setNotification(Notification.builder()
                        .setTitle(history.getTitle())
                        .setBody(history.getBody())
                        .build())
                .addAllTokens(tokens)
                .build();

     // 3) 전송
        BatchResponse batch = FirebaseMessaging.getInstance().sendEachForMulticast(message);

        // 4) 상세 이력 저장 (응답 인덱스 = 요청 인덱스)
        List<FcmHistoryDetail> details = new ArrayList<>(tokens.size());
        for (int i = 0; i < batch.getResponses().size(); i++) {
            var resp = batch.getResponses().get(i);
            String tokenStr = tokens.get(i);
            boolean ok = resp.isSuccessful();

            String status = ok ? "success" : "fail";
            String responseText;

            if (ok) {
                String msgId = resp.getMessageId();
                responseText = msgId;
                log.info("✅ [{}] FCM success | token={} | msgId={}", i, mask(tokenStr), msgId);
            } else {
                Exception ex = resp.getException();
                String code = "UNKNOWN";
                String msg = ex != null ? ex.getMessage() : "no message";

                // FirebaseMessagingException 이면 에러코드 뽑기
                if (ex instanceof com.google.firebase.messaging.FirebaseMessagingException fme) {
                    if (fme.getMessagingErrorCode() != null) {
                        code = fme.getMessagingErrorCode().name();
                    }
                }

                responseText = "[" + code + "] " + msg;
                log.warn("❌ [{}] FCM fail    | token={} | code={} | message={}",
                         i, mask(tokenStr), code, msg);
            }

            details.add(FcmHistoryDetail.builder()
                    .history(history)
                    .token(targetEntities.get(i))
                    .status(status)
                    .response(responseText)
                    .build());
        }
        fcmHistoryDetailRepository.saveAll(details);

        log.info("📦 요청={} / ✅성공={} / ❌실패={}",
                 tokens.size(), batch.getSuccessCount(), batch.getFailureCount());

        System.out.println("📦 전체 요청 수: " + tokens.size());
        System.out.println("✅ 성공 수: " + batch.getSuccessCount());
        System.out.println("❌ 실패 수: " + batch.getFailureCount());

        return batch.getSuccessCount();
    }
    
    private String mask(String s) {
        if (s == null) return "null";
        String t = s.trim();
        int n = t.length();
        if (n <= 12) return "***len=" + n;
        return t.substring(0, 6) + "..." + t.substring(n - 6) + " (len=" + n + ")";
    }
}