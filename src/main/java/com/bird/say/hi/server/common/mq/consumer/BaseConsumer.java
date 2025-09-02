package com.bird.say.hi.server.common.mq.consumer;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-09-03
 */
public interface BaseConsumer<M> {
    void handle(byte[] message) throws InvalidProtocolBufferException; // 业务逻辑

    String topic(); // Redis key
}
