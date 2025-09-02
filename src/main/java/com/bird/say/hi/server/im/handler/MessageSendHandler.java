package com.bird.say.hi.server.im.handler;

import com.bird.say.hi.im.sdk.event.TopicEvent;
import com.bird.say.hi.server.common.annotation.CommandHandler;
import com.bird.say.hi.server.common.mq.Topic;
import com.bird.say.hi.server.common.mq.TopicPublisher;
import com.bird.say.hi.server.im.enums.CommandEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-08-30
 */
@CommandHandler(CommandEnum.MESSAGE_SEND)
@Component
@Slf4j
public class MessageSendHandler implements BaseHandler {
    @Autowired
    private TopicPublisher topicPublisher;

    @Override
    public void handle(String data) {
        log.info("handle message:{}", data);
        TopicEvent.UpdateSessionInfoEvent message = TopicEvent.UpdateSessionInfoEvent.newBuilder()
                .setData(data).build();
        topicPublisher.publish(Topic.UPDATE_SESSION_INFO, message.toByteArray());
    }
}
