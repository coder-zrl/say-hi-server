package com.bird.say.hi.server.common.mq;

import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-09-02
 */
@Component
public class TopicPublisher {
    @Autowired
    private RedisCommands<String, byte[]> redisBinClient;

    public void publish(Topic topic, byte[] message) {
        redisBinClient.rpush(topic.getTopic(), message);
    }
}
