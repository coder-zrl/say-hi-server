package com.bird.say.hi.push.controller;

import com.bird.say.hi.im.sdk.MessageServiceGrpc.MessageServiceBlockingStub;
import com.bird.say.hi.im.sdk.SendMessageRequest;
import com.bird.say.hi.im.sdk.SendMessageResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zhangruilong <zhangruilong03@kuaishou.com>
 * Created on 2024-07-23
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class TestControllerTest {
    @GrpcClient("say-hi-im-service")
    private MessageServiceBlockingStub messageServiceBlockingStub;

    @Test
    void test1() {
        SendMessageResponse response = messageServiceBlockingStub.sendMessage(SendMessageRequest.getDefaultInstance());
        System.out.println(response);
    }
}