package com.example.reframe.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NettyServer {

    private final NettyConfigProps props;
    private final TopicHub hub = new TopicHub();

    private EventLoopGroup boss;
    private EventLoopGroup worker;
    private Channel serverChannel;

    public void start() {
        boss = new NioEventLoopGroup(1);
        worker = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, worker)
             .channel(NioServerSocketChannel.class)
             .childOption(ChannelOption.TCP_NODELAY, true)
             .childOption(ChannelOption.SO_KEEPALIVE, true)
             .childHandler(new ChatServerInitializer(props, hub));

            Channel ch = b.bind(props.getPort()).sync().channel();
            serverChannel = ch;
            log.info("Netty WS broker started on :{} (path={})", props.getPort(), props.getWsPath());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Netty start interrupted", e);
        } catch (Exception e) {
            throw new RuntimeException("Netty start failed", e);
        }
    }

    @PreDestroy
    public void stop() {
        log.info("Stopping Netty...");
        if (serverChannel != null) serverChannel.close();
        if (boss != null) boss.shutdownGracefully();
        if (worker != null) worker.shutdownGracefully();
    }

    // Spring 서비스에서 직접 브로드캐스트하고 싶을 때 사용
    public int broadcast(String topic, String jsonText) {
        return hub.broadcast(topic, jsonText);
    }

    public TopicHub hub() { return hub; }
}
