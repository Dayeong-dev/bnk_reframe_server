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
        String fineTunedModel = "ft:gpt-4.1-2025-04-14:green::BqrjuIj2";
        ChatRequest.Message message = new ChatRequest.Message("user", userMessage);
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
}