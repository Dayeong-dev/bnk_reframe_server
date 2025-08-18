package com.example.reframe.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolConfig;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class ChatServerInitializer extends ChannelInitializer<SocketChannel> {

    private final NettyConfigProps props;
    private final TopicHub hub;

    public ChatServerInitializer(NettyConfigProps props, TopicHub hub) {
        this.props = props;
        this.hub = hub;
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new HttpServerCodec());
        p.addLast(new HttpObjectAggregator(64 * 1024));

        p.addLast(new SimpleCorsHandler());
        p.addLast(new HttpPublishHandler(props, hub));

        p.addLast(new IdleStateHandler(0, 0, 60));

        // ✅ 쿼리스트링/하위경로 매칭 허용 (.checkStartsWith(true))
        WebSocketServerProtocolConfig cfg = WebSocketServerProtocolConfig
                .newBuilder()                         // ← 인자 없이
                .websocketPath(props.getWsPath())     // ← 여기서 경로 지정
                .maxFramePayloadLength(64 * 1024)
                .allowExtensions(true)
                .checkStartsWith(true)                // /ws?topic=... 매칭 허용
                .build();
        p.addLast(new WebSocketServerProtocolHandler(cfg));

        p.addLast(new ChatWebSocketHandler(props, hub));
    }
}
