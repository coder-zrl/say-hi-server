package com.bird.say.hi.server.common.mq.consumer;

import com.bird.say.hi.im.sdk.event.TopicEvent;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.bird.say.hi.server.common.mq.Topic.UPDATE_SESSION_INFO;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-09-03
 */
@Component
@Slf4j
public class UpdateSessionInfoConsumer implements BaseConsumer<TopicEvent.UpdateSessionInfoEvent> {

    @Override
    public void handle(byte[] message) throws InvalidProtocolBufferException {
        TopicEvent.UpdateSessionInfoEvent updateSessionInfoEvent = TopicEvent.UpdateSessionInfoEvent.parseFrom(message);
        log.info("update session info: {}", updateSessionInfoEvent.getData());
    }

    @Override
    public String topic() {
        return UPDATE_SESSION_INFO.getTopic();
    }
}
