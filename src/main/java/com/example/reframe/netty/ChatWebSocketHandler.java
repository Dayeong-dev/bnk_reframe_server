package com.example.reframe.netty;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.timeout.IdleStateEvent;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class ChatWebSocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private static final ObjectMapper M = new ObjectMapper();
    private final NettyConfigProps props;
    private final TopicHub hub;

    public ChatWebSocketHandler(NettyConfigProps props, TopicHub hub) {
        this.props = props;
        this.hub = hub;
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        hub.unsubscribeAll(ctx.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete hc) {
            String uri = hc.requestUri();
            QueryStringDecoder q = new QueryStringDecoder(uri);

            // uid 바인딩
            List<String> uids = q.parameters().get("uid");
            if (uids != null && !uids.isEmpty()) {
                String uid = uids.get(0);
                ctx.channel().attr(TopicHub.ATTR_UID).set(uid);
            }

            int cnt = 0;
            for (Map.Entry<String, List<String>> e : q.parameters().entrySet()) {
                if ("topic".equals(e.getKey())) {
                    for (String t : e.getValue()) {
                        hub.subscribe(ctx.channel(), t);
                        cnt++;
                    }
                }
            }
            if (cnt == 0) {
                ctx.writeAndFlush(new TextWebSocketFrame("{\"ok\":true,\"msg\":\"connected_no_topic\"}"));
            }
        } else if (evt instanceof IdleStateEvent) {
            ctx.writeAndFlush(new PingWebSocketFrame(Unpooled.copiedBuffer("ping", StandardCharsets.UTF_8)));
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame f) {
        if (f instanceof CloseWebSocketFrame) { ctx.close(); return; }
        if (f instanceof PingWebSocketFrame) { ctx.writeAndFlush(new PongWebSocketFrame(f.content().retain())); return; }
        if (!(f instanceof TextWebSocketFrame txt)) return;

        JsonNode root;
        try { root = M.readTree(txt.text()); } catch (Exception e) { return; }
        String op = root.path("op").asText("");

        switch (op) {
            case "subscribe" -> {
                if (root.has("topics") && root.get("topics").isArray()) {
                    root.get("topics").forEach(n -> hub.subscribe(ctx.channel(), n.asText()));
                    ack(ctx, "subscribed");
                }
            }
            case "unsubscribe" -> {
                if (root.has("topics") && root.get("topics").isArray()) {
                    root.get("topics").forEach(n -> hub.unsubscribe(ctx.channel(), n.asText()));
                    ack(ctx, "unsubscribed");
                }
            }
            case "publish" -> {
                String token = root.path("token").asText("");
                boolean tokenOk = props.checkSecret(token);
                if (!tokenOk) { err(ctx, "unauthorized"); return; }

                String topic = root.path("topic").asText("");
                JsonNode data = root.get("data");
                if (topic.isEmpty() || data == null) { err(ctx, "invalid_publish"); return; }

                boolean excludeSelf = root.path("excludeSelf").asBoolean(false);
                String issuer = root.path("issuer").asText("");
                if ((issuer == null || issuer.isEmpty()) && data.has("issuer")) {
                    issuer = data.path("issuer").asText("");
                }

                int receivers;
                try {
                    String payload = M.writeValueAsString(data);
                    if (excludeSelf && issuer != null && !issuer.isEmpty()) {
                        receivers = hub.broadcastExcept(topic, payload, issuer);
                    } else {
                        receivers = hub.broadcast(topic, payload);
                    }
                } catch (Exception ex) {
                    err(ctx, "encode_error");
                    return;
                }
                ack(ctx, "published", receivers);
            }
            default -> { /* no-op */ }
        } 
    }

    private void ack(ChannelHandlerContext ctx, String msg) {
        ctx.writeAndFlush(new TextWebSocketFrame("{\"ok\":true,\"msg\":\"" + msg + "\"}"));
    }
    private void ack(ChannelHandlerContext ctx, String msg, int n) {
        ctx.writeAndFlush(new TextWebSocketFrame("{\"ok\":true,\"msg\":\"" + msg + "\",\"receivers\":" + n + "}"));
    }
    private void err(ChannelHandlerContext ctx, String msg) {
        ctx.writeAndFlush(new TextWebSocketFrame("{\"ok\":false,\"error\":\"" + msg + "\"}"));
    }
}
