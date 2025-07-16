package com.example.reframe.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reframe.service.OpenAIService2;

@RestController
@RequestMapping("/api/chat2")
public class ChatController2 {

    private final OpenAIService2 openAIService2;

    public ChatController2(OpenAIService2 openAIService2) {
        this.openAIService2 = openAIService2;
    }

    @PostMapping
    public String chat(@RequestBody String message) {
        return openAIService2.askChatGPT(message);
    }
}