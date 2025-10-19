package com.bird.say.hi.im.message.rpc;

import com.bird.say.hi.common.grpc.GrpcTemplate;
import com.bird.say.hi.common.grpc.GrpcTemplate.ProcessFunc;
import com.bird.say.hi.common.utils.ProtoUtils;
import com.bird.say.hi.im.sdk.MessageServiceGrpc;
import com.bird.say.hi.im.sdk.SendMessageRequest;
import com.bird.say.hi.im.sdk.SendMessageResponse;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.function.Consumer;

import static com.bird.say.hi.common.grpc.GrpcResponseProcess.getResponseProcess;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-07
 */
@GrpcService
@Slf4j
public class MessageServiceImpl extends MessageServiceGrpc.MessageServiceImplBase {
    @Override
    public void sendMessage(SendMessageRequest request, StreamObserver<SendMessageResponse> responseObserver) {
        String invokeName = "ChatToBCsGrpcService.sendMessage";
        // 参数判断
        Consumer<SendMessageRequest> checkParams = req -> {
            checkArgument(req.getUserId() > 0, "invalid userId");
        };

        ProcessFunc<SendMessageRequest, SendMessageResponse> processFunc = req -> {
            log.info("sendMessage start, request:{}", ProtoUtils.oneLine(request));
            return SendMessageResponse.newBuilder()
                    .setErrorCode(1)
                    .setErrorMsg("success")
                    .build();
        };

        GrpcTemplate.execute(invokeName, request, checkParams, processFunc,
                getResponseProcess(SendMessageResponse.class), responseObserver);
    }
}
