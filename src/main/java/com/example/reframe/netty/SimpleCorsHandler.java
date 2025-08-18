package com.example.reframe.netty;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;

import static io.netty.handler.codec.http.HttpHeaderNames.*;

public class SimpleCorsHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof HttpRequest req) {
            if (HttpMethod.OPTIONS.equals(req.method())) {
                FullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
                addCors(resp);
                ctx.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE);
                return;
            }
        }
        ctx.fireChannelRead(msg);
    }

    public static void addCors(HttpResponse resp) {
        resp.headers().set(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        resp.headers().set(ACCESS_CONTROL_ALLOW_METHODS, "GET,POST,OPTIONS");
        resp.headers().set(ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type,X-Auth-Token");
    }
}
