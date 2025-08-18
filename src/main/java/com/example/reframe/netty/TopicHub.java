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
    public static final AttributeKey<Set<String>> ATTR_TOPICS = AttributeKey.valueOf("SUB_TOPICS");

    public void subscribe(Channel ch, String topic) {
        topics.computeIfAbsent(topic, t -> new DefaultChannelGroup(GlobalEventExecutor.INSTANCE))
              .add(ch);
        Set<String> set = ch.attr(ATTR_TOPICS).get();
        if (set == null) {
            set = Collections.synchronizedSet(new HashSet<>());
            ch.attr(ATTR_TOPICS).set(set);
        }
        set.add(topic);
        System.out.println("[WS] SUB topic=" + topic + " size=" + topics.get(topic).size());
    }

    public Map<String,Integer> stats() {
        Map<String,Integer> m = new HashMap<>();
        topics.forEach((k,v) -> m.put(k, v.size()));
        return m;
    }

    public void unsubscribe(Channel ch, String topic) {
        Optional.ofNullable(topics.get(topic)).ifPresent(g -> g.remove(ch));
        Optional.ofNullable(ch.attr(ATTR_TOPICS).get()).ifPresent(s -> s.remove(topic));
    }

    public void unsubscribeAll(Channel ch) {
        Set<String> set = ch.attr(ATTR_TOPICS).get();
        if (set != null) {
            for (String t : set) Optional.ofNullable(topics.get(t)).ifPresent(g -> g.remove(ch));
            set.clear();
        }
    }

    public int broadcast(String topic, String jsonText) {
        ChannelGroup g = topics.get(topic);
        if (g == null) return 0;
        g.writeAndFlush(new TextWebSocketFrame(jsonText));
        return g.size();
    }
}
