package com.bird.say.hi.gateway.controller;

import com.bird.say.hi.common.stub.MessageServiceStub;
import com.bird.say.hi.common.utils.ProtoUtils;
import com.bird.say.hi.im.sdk.SendMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bird.say.hi.common.redis.RedisNameSpace.redisLock;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-07
 */
@RestController
public class TestController {
    @Autowired
    private MessageServiceStub messageServiceStub;

    @GetMapping("/test")
    public String test() {
        return "hello world";
    }

    @GetMapping("/testGrpc")
    public String testGrpc() {
        SendMessageResponse sendMessageResponse = messageServiceStub.sendMessage();
        return ProtoUtils.oneLine(sendMessageResponse);
    }

    @GetMapping("/testRedis")
    public String testRedis() {
        redisLock.getSyncClient().set(redisLock.toRedisKey("test"), "111");
        return redisLock.getSyncClient().get(redisLock.toRedisKey("test"));
    }
}
