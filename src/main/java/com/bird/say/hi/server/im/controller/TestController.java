package com.bird.say.hi.server.im.controller;

import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-08-30
 */
@RestController
@Slf4j
public class TestController {
    @Autowired
    private RedisCommands<String, String> redisClient;

    @RequestMapping("/test")
    public String test() {
        String set = redisClient.set("test", "hahaha");
        log.info("set result:{}", set);

        return redisClient.get("test");
    }
}
