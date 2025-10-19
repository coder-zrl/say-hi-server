package com.bird.say.hi.gateway.websocket.handler;

import jakarta.websocket.Session;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-09
 */
@Service
public class WebSocketChannelHandler {
    /**
     * 存储在线的长连接会话
     */
    private Map<String, Session> onlineSessionClientMap = new ConcurrentHashMap<>();

    public void addOnlineSession(String userId, Session session) {
        onlineSessionClientMap.put(userId, session);
    }

    public void delOnlineSession(String userId, Session session) {
        onlineSessionClientMap.remove(userId, session);
    }
}