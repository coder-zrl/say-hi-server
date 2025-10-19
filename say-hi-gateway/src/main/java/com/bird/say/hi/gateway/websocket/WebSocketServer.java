package com.bird.say.hi.gateway.websocket;

import cn.hutool.json.JSONUtil;
import com.bird.say.hi.gateway.model.LongLinkBody;
import com.bird.say.hi.gateway.websocket.handler.WebSocketChannelHandler;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-09
 */
@Slf4j
@Component
@ServerEndpoint(value = "/websocket/{userId}")
public class WebSocketServer {
    private static WebSocketChannelHandler channelHandler;
    private String userId;
    private Session session;

    @Autowired
    private void setChannelHandler(WebSocketChannelHandler channelHandler) {
        WebSocketServer.channelHandler = channelHandler;
    }

    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) {
        // 当前session会话会自动生成一个id，从0开始累加
        log.info("onOpen session:{}, userId:{}", JSONUtil.toJsonStr(session), userId);
        channelHandler.addOnlineSession(userId, session);
//        this.userId = userId;
//        this.session = session;
//        channelHandler.addOnlineSession(userId, this);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("userId") String userId, Session session) {
        channelHandler.delOnlineSession(userId, session);
        log.info("close connection success, sessionId:{}, userId:{}", session.getId(), userId);
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        LongLinkBody longLinkBody = JSONUtil.toBean(message, LongLinkBody.class);
        log.info("receive message, message:{}, longLinkBody:{}, session:{}", message, longLinkBody, session);
//        commandRouter.route(longLinkBody);
    }

    /**
     * 发生错误调用的方法
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("WebSocketServer onError, sessionId:{}", session.getId(), error);
    }
}