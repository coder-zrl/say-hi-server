package com.bird.say.hi.server.common.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-08-30
 */
@Configuration
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public RedisCommands<String, String> redisClient() {
        RedisURI redisUri = RedisURI.builder()
                .withHost(host)
                .withPort(port)
                .withTimeout(Duration.ofSeconds(10))
                .build();
        return RedisClient.create(redisUri).connect().sync();
    }

    @Bean
    public RedisCommands<String, byte[]> redisBinClient() {
        RedisURI redisUri = RedisURI.builder()
                .withHost(host)
                .withPort(port)
                .withTimeout(Duration.ofSeconds(10))
                .build();

        // 指定 Key 用 UTF-8，Value 用原始字节
        return RedisClient.create(redisUri)
                .connect(RedisCodec.of(StringCodec.UTF8, ByteArrayCodec.INSTANCE))
                .sync();
    }
}
