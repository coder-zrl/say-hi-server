package com.bird.say.hi.server.im.handler;

import com.bird.say.hi.server.common.annotation.CommandHandler;
import com.bird.say.hi.server.im.enums.CommandEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-08-30
 */
@CommandHandler(CommandEnum.MESSAGE_SEND)
@Component
@Slf4j
public class MessageSendHandler implements BaseHandler{
    @Override
    public void handle(String data) {
        log.info("handle message:{}", data);
    }
}
