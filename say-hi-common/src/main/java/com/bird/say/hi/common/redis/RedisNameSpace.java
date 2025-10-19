package com.bird.say.hi.common.redis;

import com.bird.say.hi.common.config.RedisConnectionHolder;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-12
 */
public enum RedisNameSpace {
    redisLock(null, "redisLock"),
    test(null, "test"),
    ;

    private final StatefulRedisConnection<String, String> connection;
    private final String keyPrefix;

    private StatefulRedisConnection<String, String> getConnection() {
        return RedisConnectionHolder.getConnection();
    }

    public RedisAsyncCommands<String, String> getAsyncClient() {
        return getConnection().async();
    }

    public RedisCommands<String, String> getSyncClient() {
        return getConnection().sync();
    }

    RedisNameSpace(StatefulRedisConnection<String, String> connection, String keyPrefix) {
        this.connection = connection;
        this.keyPrefix = keyPrefix;
    }

    public String toRedisKey(Object... values) {
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append(keyPrefix);
        for (Object value : values) {
            stringBuffer.append("_");
            stringBuffer.append(value);
        }
        return stringBuffer.toString();
    }
}