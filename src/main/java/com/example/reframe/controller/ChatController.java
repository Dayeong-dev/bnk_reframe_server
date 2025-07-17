package com.example.reframe.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reframe.service.OpenAIService;
import com.example.reframe.service.OpenAIService2;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final OpenAIService openAIService;
    private final OpenAIService2 openAIService2;

    public ChatController(OpenAIService openAIService, OpenAIService2 openAIService2) {
        this.openAIService = openAIService;
        this.openAIService2 = openAIService2;
    }

    //삭제예정
    @PostMapping("/normal")
    public String chat(@RequestBody String message) {
        return openAIService.askChatGPT(message);
    }
    
    @PostMapping("/fineTuned")
    public String chat2(@RequestBody String message) {
        return openAIService.askChatGPT2(message);
    }
    
    @PostMapping("/memory")
    public String chat3(HttpSession session,@RequestBody String message) {
    	String sessionId = session.getId(); // 세션 ID 가져오기
        return openAIService2.askChatGPTWithHistory(sessionId,message);
    }
    
    
    
}