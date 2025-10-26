package com.bird.say.hi.im.message.rpc;

import com.bird.say.hi.common.grpc.GrpcTemplate;
import com.bird.say.hi.common.grpc.GrpcTemplate.ProcessFunc;
import com.bird.say.hi.common.utils.ProtoUtils;
import com.bird.say.hi.im.message.service.MessageService;
import com.bird.say.hi.im.sdk.DeleteMessageRequest;
import com.bird.say.hi.im.sdk.DeleteMessageResponse;
import com.bird.say.hi.im.sdk.MessageServiceGrpc;
import com.bird.say.hi.im.sdk.SendMessageRequest;
import com.bird.say.hi.im.sdk.SendMessageResponse;
import io.grpc.stub.StreamObserver;
import jakarta.annotation.Resource;
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
    @Resource
    private MessageService messageService;

    @Override
    public void sendMessage(SendMessageRequest request, StreamObserver<SendMessageResponse> responseObserver) {
        String invokeName = "ChatToBCsGrpcService.sendMessage";
        // 参数判断
        Consumer<SendMessageRequest> checkParams = req -> {
            checkArgument(req.getUserId() > 0, "invalid userId");
        };

        ProcessFunc<SendMessageRequest, SendMessageResponse> processFunc = req -> {
            return messageService.sendMessage(req);
        };

        GrpcTemplate.execute(invokeName, request, checkParams, processFunc,
                getResponseProcess(SendMessageResponse.class), responseObserver);
    }
    
    @Override
    public void deleteMessage(DeleteMessageRequest request, StreamObserver<DeleteMessageResponse> responseObserver) {
        String invokeName = "ChatToBCsGrpcService.deleteMessage";
        // 参数判断
        Consumer<DeleteMessageRequest> checkParams = req -> {
            checkArgument(req.getChatId() > 0, "invalid chatId");
            checkArgument(req.getSeqId() > 0, "invalid seqId");
        };

        ProcessFunc<DeleteMessageRequest, DeleteMessageResponse> processFunc = req -> {
            return messageService.deleteMessage(req);
        };

        GrpcTemplate.execute(invokeName, request, checkParams, processFunc,
                getResponseProcess(DeleteMessageResponse.class), responseObserver);
    }
}