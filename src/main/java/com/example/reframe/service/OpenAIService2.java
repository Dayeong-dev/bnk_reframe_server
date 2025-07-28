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
    private static final String fineTunedModel = "ft:gpt-4.1-2025-04-14:green:bnk:BvvK7sqc";
    private static final int MAX_MESSAGE_HISTORY = 10;
    // 세션ID별 대화 로그 저장
    private final Map<String, List<Message>> conversationHistoryMap = new HashMap<>();

    public String askChatGPTWithHistory(String sessionId, String userMessage) {
        RestTemplate restTemplate = new RestTemplate();
        

        // 세션별 히스토리를 가져오거나 새로 생성
        List<Message> messages = conversationHistoryMap.computeIfAbsent(sessionId, k -> new ArrayList<>());

        // 처음 대화 시 system 메시지를 추가
        if (messages.isEmpty()) {
            messages.add(new Message("system", "너는 BNK 부산은행 챗봇이다.  \r\n"
            		+ "친절하고 정중하게 답하며, 고객이 쉽게 이해할 수 있도록 설명한다.  \r\n"
            		+ "은행 상품, 서비스, 가입 방법, 혜택, 수수료, 이벤트 등 BNK 부산은행 관련 정보와 이 모델에 학습된 데이터로만 대답한다.  \r\n"
            		+ "개인정보나 계좌 정보 등 민감한 내용은 답하지 않는다.  \r\n"
            		+ "답변은 간결하고 명확하게 작성하며, 과장된 표현이나 추측성 답변을 하지 않는다."));
        }

        // 현재 user 메시지 추가
        messages.add(new Message("user", userMessage));
        System.out.println(userMessage);
        
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
