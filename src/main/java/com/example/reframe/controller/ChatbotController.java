package com.example.reframe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatbotController {

    @GetMapping("/chatbot")
    public String chatbot() {
        return "user/chatbot"; // 챗봇 페이지
    }
    
    @GetMapping("/game")
    public String game() {
        return "user/game"; // 게임 페이지
    }
    
}