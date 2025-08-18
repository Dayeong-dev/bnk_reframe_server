package com.example.reframe.netty;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class NettyConfigProps {
    @Value("${netty.port:8081}")
    private int port;

    @Value("${netty.ws-path:/ws}")
    private String wsPath;

    @Value("${netty.publish-secret:}")
    private String publishSecret;

    public boolean checkSecret(String token) {
        return publishSecret == null || publishSecret.isBlank() || publishSecret.equals(token);
    }
}
