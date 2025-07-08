package com.example.reframe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatbotController {

    @GetMapping("/")
    public String index() {
        return "user/index"; // 메인 페이지
    }
    
    @GetMapping("/chat")
    public String chat() {
        return "user/chat"; // 챗봇 페이지
    }

    @GetMapping("/chatbot")
    public String chatbot() {
        return "user/chatbot"; // 챗봇 페이지
    }
}