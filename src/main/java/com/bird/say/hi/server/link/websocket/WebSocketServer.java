package com.bird.say.hi.server.link.websocket;

import cn.hutool.json.JSONUtil;
import com.bird.say.hi.server.im.message.service.CommandRouter;
import com.bird.say.hi.server.link.handler.WebSocketChannelHandler;
import com.bird.say.hi.server.link.model.LongLinkBody;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * WebSocket 服务器，用来处理长连接
 * configurator 用于解决 @ServerEndpoint 不支持自动注入 Spring Bean 的问题
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-08-30
 */
@Slf4j
@Component
@ServerEndpoint(value = "/websocket/{userId}")
public class WebSocketServer {
    private static CommandRouter commandRouter;
    private static WebSocketChannelHandler channelHandler;
    private String userId;
    private Session session;

    @Autowired
    private void setCommandRouter(CommandRouter commandRouter) {
        WebSocketServer.commandRouter = commandRouter;
    }
    @Autowired
    private void setChannelHandler(WebSocketChannelHandler channelHandler) {
        WebSocketServer.channelHandler = channelHandler;
    }

    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) {
        // 当前session会话会自动生成一个id，从0开始累加
        log.info("onOpen session_id:{}, userId:{}", session.getId(), userId);
        channelHandler.addOnlineSession(userId, session);
//        this.userId = userId;
//        this.session = session;
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("userId") String userId, Session session) {
        channelHandler.delOnlineSession(userId, session);
        log.info("close connection success, session_id:{}, userId:{}", session.getId(), userId);
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        LongLinkBody longLinkBody = JSONUtil.toBean(message, LongLinkBody.class);
        log.info("receive message, message:{}, longLinkBody:{}, session:{}", message, longLinkBody, session);
        commandRouter.route(longLinkBody);
    }

    /**
     * 发生错误调用的方法
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("WebSocket发生错误，错误信息为：" + error.getMessage());
        error.printStackTrace();
    }
}
