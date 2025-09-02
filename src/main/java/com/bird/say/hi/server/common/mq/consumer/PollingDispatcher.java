package com.bird.say.hi.server.common.mq.consumer;

import io.lettuce.core.api.sync.RedisCommands;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-09-03
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PollingDispatcher {
    private final RedisCommands<String, byte[]> redisBinClient;
    private final List<BaseConsumer<?>> consumers;
    private final ScheduledExecutorService pool = Executors.newScheduledThreadPool(4);

    @PostConstruct
    public void start() {
        log.info("start polling dispatcher, consumers:{}", consumers);
        consumers.forEach(c -> pool.scheduleAtFixedRate(() -> loop(c), 0, 20, TimeUnit.SECONDS));
    }

    private <M> void loop(BaseConsumer<M> consumer) {
        String topic = consumer.topic();
        if (!Thread.currentThread().isInterrupted()) {
            log.info("consume {} start", topic);
            try {
                byte[] message = redisBinClient.lpop(topic);
                if (message != null) {
                    consumer.handle(message);
                }
            } catch (Exception e) {
                log.error("consume {} error", topic, e);
            }
        }
    }
}
