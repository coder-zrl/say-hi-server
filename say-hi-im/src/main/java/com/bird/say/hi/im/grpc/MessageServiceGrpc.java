package com.bird.say.hi.im.grpc;

import com.bird.say.hi.component.grpc.GrpcTemplate;
import com.bird.say.hi.component.grpc.GrpcTemplate.ProcessFunc;
import com.bird.say.hi.im.sdk.MessageServiceGrpc.MessageServiceImplBase;
import com.bird.say.hi.im.sdk.SendMessageRequest;
import com.bird.say.hi.im.sdk.SendMessageResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.function.Consumer;

import static com.bird.say.hi.component.grpc.GrpcResponseProcess.getResponseProcess;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author zhangruilong <zhangruilong03@kuaishou.com>
 * Created on 2024-07-23
 */
@GrpcService
public class MessageServiceGrpc extends MessageServiceImplBase {

    @Override
    public void sendMessage(SendMessageRequest request, StreamObserver<SendMessageResponse> responseObserver) {
        String invokeName = "ChatToBCsGrpcService.sendMessage";
        // 参数判断
        Consumer<SendMessageRequest> checkParams = req -> {
            checkArgument(req.getUserId() > 0, "invalid userId");
        };

        ProcessFunc<SendMessageRequest, SendMessageResponse> processFunc = req -> {
            return SendMessageResponse.newBuilder().setErrorCode(666).build();
        };

        GrpcTemplate.execute(invokeName, request, checkParams, processFunc,
                getResponseProcess(SendMessageResponse.class), responseObserver);
    }
}
