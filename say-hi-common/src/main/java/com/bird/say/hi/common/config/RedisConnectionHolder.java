package com.bird.say.hi.common.config;

import io.lettuce.core.api.StatefulRedisConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-12
 */
@Component
public class RedisConnectionHolder {

    private static StatefulRedisConnection<String, String> connection;

    @Autowired
    public RedisConnectionHolder(StatefulRedisConnection<String, String> redisConnection) {
        RedisConnectionHolder.connection = redisConnection;
    }

    public static StatefulRedisConnection<String, String> getConnection() {
        if (connection == null) {
            throw new IllegalStateException("Redis连接尚未初始化，请检查Spring容器是否已成功加载该Bean。");
        }
        return connection;
    }
}
