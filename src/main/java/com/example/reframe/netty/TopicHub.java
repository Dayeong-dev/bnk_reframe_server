package com.example.reframe.netty;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TopicHub {

    private final Map<String, ChannelGroup> topics = new ConcurrentHashMap<>();

    // ✅ 채널에 바인딩된 유저 UID (Handshake에서 셋업)
    public static final AttributeKey<String> ATTR_UID = AttributeKey.valueOf("USER_UID");
    public static final AttributeKey<Set<String>> ATTR_TOPICS = AttributeKey.valueOf("SUB_TOPICS");

    public int sizeOf(String topic) {
        ChannelGroup g = topics.get(topic);
        return g == null ? 0 : g.size();
    }

    private void notifyPresence(String topic) {
        if (!topic.endsWith(".presence")) return;
        ChannelGroup g = topics.get(topic);
        if (g == null) return;
        String msg = "{\"type\":\"presence\",\"count\":" + g.size() + "}";
        g.writeAndFlush(new TextWebSocketFrame(msg));
    }

    public void subscribe(Channel ch, String topic) {
        topics.computeIfAbsent(topic, t -> new DefaultChannelGroup(GlobalEventExecutor.INSTANCE))
                .add(ch);
        Set<String> set = ch.attr(ATTR_TOPICS).get();
        if (set == null) {
            set = Collections.synchronizedSet(new HashSet<>());
            ch.attr(ATTR_TOPICS).set(set);
        }
        set.add(topic);

        notifyPresence(topic);
    }

    public Map<String,Integer> stats() {
        Map<String,Integer> m = new HashMap<>();
        topics.forEach((k,v) -> m.put(k, v.size()));
        return m;
    }

    public void unsubscribe(Channel ch, String topic) {
        Optional.ofNullable(topics.get(topic)).ifPresent(g -> g.remove(ch));
        Optional.ofNullable(ch.attr(ATTR_TOPICS).get()).ifPresent(s -> s.remove(topic));
        notifyPresence(topic);
    }

    public void unsubscribeAll(Channel ch) {
        Set<String> set = ch.attr(ATTR_TOPICS).get();
        if (set != null) {
            for (String t : set) {
                Optional.ofNullable(topics.get(t)).ifPresent(g -> g.remove(ch));
                notifyPresence(t);
            }
            set.clear();
        }
    }

    /** 기존: 모든 구독자에게 브로드캐스트 */
    public int broadcast(String topic, String jsonText) {
        ChannelGroup g = topics.get(topic);
        if (g == null) return 0;
        g.writeAndFlush(new TextWebSocketFrame(jsonText));
        return g.size();
    }

    /** ✅ 추가: 특정 uid를 제외하고 브로드캐스트 */
    public int broadcastExcept(String topic, String jsonText, String excludeUid) {
        ChannelGroup g = topics.get(topic);
        if (g == null) return 0;

        int cnt = 0;
        TextWebSocketFrame frame = new TextWebSocketFrame(jsonText);
        for (Channel ch : g) {
            String uid = ch.attr(ATTR_UID).get();
            if (excludeUid != null && excludeUid.equals(uid)) {
                continue; // 발급자 제외
            }
            ch.writeAndFlush(frame.retainedDuplicate());
            cnt++;
        }
        frame.release();
        return cnt;
    }
}
