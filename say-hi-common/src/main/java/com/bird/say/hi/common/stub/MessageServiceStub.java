package com.bird.say.hi.common.stub;

import com.bird.say.hi.common.utils.ProtoUtils;
import com.bird.say.hi.im.sdk.MessageServiceGrpc;
import com.bird.say.hi.im.sdk.SendMessageRequest;
import com.bird.say.hi.im.sdk.SendMessageResponse;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-12
 */
@Service
@Slf4j
public class MessageServiceStub {
    //    @GrpcClient("say-hi-im-message")
//    private MessageServiceGrpc.MessageServiceBlockingStub messageServiceBlockingStub;
    @GrpcClient("say-hi-im-message")
    private MessageServiceGrpc.MessageServiceBlockingStub messageServiceBlockingStub;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Nullable
    public SendMessageResponse sendMessage() {
//        List<ServiceInstance> instances = discoveryClient.getInstances("say-hi-im-message");

        SendMessageRequest request = SendMessageRequest.newBuilder()
                .setUserId(111)
                .build();
        try {
            return messageServiceBlockingStub.sendMessage(request);
        } catch (Exception e) {
            log.warn("sendMessage fail request:{}", ProtoUtils.oneLine(request), e);
        }
        return null;
    }
}
