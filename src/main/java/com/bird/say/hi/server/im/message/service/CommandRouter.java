package com.bird.say.hi.server.im.message.service;

import com.bird.say.hi.server.common.annotation.CommandHandler;
import com.bird.say.hi.server.im.enums.CommandEnum;
import com.bird.say.hi.server.im.handler.BaseHandler;
import com.bird.say.hi.server.link.model.LongLinkBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-08-30
 */
@Component
@Slf4j
public class CommandRouter {
    private final Map<CommandEnum, BaseHandler> handlers = new HashMap<>();

    @Autowired
    public CommandRouter(List<BaseHandler> annotatedHandlers) {
        for (BaseHandler handler : annotatedHandlers) {
            CommandHandler annotation = handler.getClass().getAnnotation(CommandHandler.class);
            if (annotation != null) {
                handlers.put(annotation.value(), handler);
            }
        }
    }

    public void route(LongLinkBody body) {
        CommandEnum commandEnum = CommandEnum.fromCommand(body.getCommand());
        if (commandEnum == null) {
            log.warn("get commandEnum failed, body:{}", body);
            return;
        }
        BaseHandler handler = handlers.get(commandEnum);
        if (handler != null) {
            handler.handle(body.data);
        }
    }
}
