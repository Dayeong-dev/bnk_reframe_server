package com.example.reframe.netty;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpPublishHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final ObjectMapper M = new ObjectMapper();
    private final NettyConfigProps props;
    private final TopicHub hub;

    public HttpPublishHandler(NettyConfigProps props, TopicHub hub) {
        this.props = props;
        this.hub = hub;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) {
        if (req.uri().startsWith("/api/publish")) {
            if (!HttpMethod.POST.equals(req.method())) {
                write(ctx, METHOD_NOT_ALLOWED, "{\"ok\":false,\"error\":\"method\"}");
                return;
            }

            String token = req.headers().get("X-Auth-Token", "");
            if (!props.checkSecret(token)) {
                write(ctx, UNAUTHORIZED, "{\"ok\":false,\"error\":\"unauthorized\"}");
                return;
            }

            String body = req.content().toString(StandardCharsets.UTF_8);
            JsonNode root;
            try {
                root = M.readTree(body);
            } catch (Exception e) {
                write(ctx, BAD_REQUEST, "{\"ok\":false,\"error\":\"invalid_json\"}");
                return;
            }

            String topic = root.path("topic").asText("");
            JsonNode data = root.get("data");
            if (topic.isEmpty() || data == null) {
                write(ctx, BAD_REQUEST, "{\"ok\":false,\"error\":\"invalid_payload\"}");
                return;
            }

            int receivers;
            try {
                receivers = hub.broadcast(topic, M.writeValueAsString(data));
            } catch (Exception e) {
                write(ctx, INTERNAL_SERVER_ERROR, "{\"ok\":false,\"error\":\"encode_error\"}");
                return;
            }

            write(ctx, OK, "{\"ok\":true,\"receivers\":" + receivers + "}");
        } else {
            // 다른 요청은 파이프라인에 넘김
            ctx.fireChannelRead(req.retain());
        }
    }

    private void write(ChannelHandlerContext ctx, HttpResponseStatus status, String json) {
        byte[] b = json.getBytes(StandardCharsets.UTF_8);
        FullHttpResponse resp = new DefaultFullHttpResponse(HTTP_1_1, status, Unpooled.wrappedBuffer(b));
        resp.headers().set(CONTENT_TYPE, "application/json; charset=UTF-8");
        resp.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, b.length);

        // ✅ 응답에만 CORS 헤더 추가
        SimpleCorsHandler.addCors(resp);

        ctx.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE);
    }
}
