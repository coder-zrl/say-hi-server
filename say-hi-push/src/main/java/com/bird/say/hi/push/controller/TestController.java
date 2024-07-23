package com.bird.say.hi.push.controller;

import com.bird.say.hi.component.utils.ProtoUtils;
import com.bird.say.hi.im.sdk.MessageServiceGrpc.MessageServiceBlockingStub;
import com.bird.say.hi.im.sdk.SendMessageRequest;
import com.bird.say.hi.im.sdk.SendMessageResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangruilong <zhangruilong03@kuaishou.com>
 * Created on 2024-07-23
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @GrpcClient("say-hi-im-service")
    private MessageServiceBlockingStub messageServiceBlockingStub;

    @GetMapping("/testGrpc")
    public Object test() {
        SendMessageResponse response = messageServiceBlockingStub.sendMessage(SendMessageRequest.getDefaultInstance());
        return ProtoUtils.oneLine(response);
    }
}
