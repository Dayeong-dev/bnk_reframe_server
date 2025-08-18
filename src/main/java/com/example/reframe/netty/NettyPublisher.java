package com.example.reframe.netty;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NettyPublisher {

    private final NettyServer server;
    private static final ObjectMapper M = new ObjectMapper();

    public int publish(String topic, Object payload) {
        try {
            String json = M.writeValueAsString(payload);
            return server.broadcast(topic, json);
        } catch (Exception e) {
            throw new RuntimeException("publish encode error", e);
        }
    }
}
