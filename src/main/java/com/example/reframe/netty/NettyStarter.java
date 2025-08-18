package com.example.reframe.netty;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NettyStarter {
    private final NettyServer server;

    @PostConstruct
    public void init() {
        server.start(); // Spring Boot가 뜰 때 Netty도 함께 시작
    }
}
