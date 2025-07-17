package com.example.reframe.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.reframe.util.ChatRequest;
import com.example.reframe.util.ChatRequest.Message;
import com.example.reframe.util.ChatResponse;

@Service
public class OpenAIService2 {

    @Value("${openai.api.key}")
    private String apiKey;

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String fineTunedModel = "ft:gpt-4.1-2025-04-14:green:bnk123:Bt6Iw477";
    private static final int MAX_MESSAGE_HISTORY = 20;
    // 세션ID별 대화 로그 저장
    private final Map<String, List<Message>> conversationHistoryMap = new HashMap<>();

    public String askChatGPTWithHistory(String sessionId, String userMessage) {
        RestTemplate restTemplate = new RestTemplate();
        

        // 세션별 히스토리를 가져오거나 새로 생성
        List<Message> messages = conversationHistoryMap.computeIfAbsent(sessionId, k -> new ArrayList<>());

        // 처음 대화 시 system 메시지를 추가
        if (messages.isEmpty()) {
            messages.add(new Message("system", "너는 BNK 부산은행의 정보를 안내하는 챗봇이야. 'bnk에서'로 시작하는 질문은 항상 학습된 데이터로 답하고, 학습된 데이터가 없으면 모르겠다고 해."));
        }

        // 현재 user 메시지 추가
        messages.add(new Message("user", userMessage.startsWith("bnk에서") ? userMessage : "bnk에서 " + userMessage));
        System.out.println(messages);
        
     // 메시지 수 제한 초과 시 앞에서부터 제거 (시스템 메시지는 유지)
        while (messages.size() > MAX_MESSAGE_HISTORY + 1) { // +1은 system
            messages.remove(1); // index 0은 system 메시지이므로 1부터 삭제
        }
        
        // GPT 요청 생성
        ChatRequest request = new ChatRequest(fineTunedModel, messages);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        HttpEntity<ChatRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ChatResponse> response = restTemplate.exchange(
            API_URL,
            HttpMethod.POST,
            entity,
            ChatResponse.class
        );

        String assistantReply = response.getBody()
                                        .getChoices()
                                        .get(0)
                                        .getMessage()
                                        .getContent();

        // assistant 응답도 history에 추가
        messages.add(new Message("assistant", assistantReply));
        System.out.println(assistantReply);
        return assistantReply;
    }
}
