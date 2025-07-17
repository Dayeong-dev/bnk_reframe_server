package com.example.reframe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.reframe.util.ChatRequest;
import com.example.reframe.util.ChatResponse;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    public String askChatGPT(String userMessage) {
        RestTemplate restTemplate = new RestTemplate();
        String fineTunedModel = "ft:gpt-4.1-2025-04-14:green:bnk123:Bt6Iw477";
        ChatRequest.Message message = new ChatRequest.Message("user", userMessage);
        System.out.println(message);
        ChatRequest request = new ChatRequest(fineTunedModel, List.of(message));

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

        return response.getBody()
                       .getChoices()
                       .get(0)
                       .getMessage()
                       .getContent();
    }
    
    public String askChatGPT2(String userMessage) {
        RestTemplate restTemplate = new RestTemplate();
        String fineTunedModel = "ft:gpt-4.1-2025-04-14:green:bnk123:Bt6Iw477";

        ChatRequest.Message systemMessage = new ChatRequest.Message(
            "system", 
            "너는 BNK 부산은행의 정보를 안내하는 챗봇이야. 'bnk에서'로 시작하는 질문은 항상 학습된 데이터로 답하고 학습된 데이터가 없으면 모르겠다고 해"
        );

        ChatRequest.Message userMessageObj = new ChatRequest.Message(
            "user", 
            userMessage.startsWith("bnk에서") ? userMessage : "bnk에서 " + userMessage
        );
        
        

        ChatRequest request = new ChatRequest(
            fineTunedModel, 
            List.of(systemMessage, userMessageObj)
        );

        System.out.println("전송되는 전체 메시지: ");
        System.out.println(request);
        
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

        return response.getBody()
                       .getChoices()
                       .get(0)
                       .getMessage()
                       .getContent();
    }
    
    
}