package com.bird.say.hi.user.rpc;

import com.bird.say.hi.common.grpc.GrpcTemplate;
import com.bird.say.hi.im.sdk.RegisterRequest;
import com.bird.say.hi.im.sdk.RegisterResponse;
import com.bird.say.hi.im.sdk.UserServiceGrpc;
import com.bird.say.hi.user.service.UserService;
import com.google.common.base.Preconditions;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Consumer;

import static com.bird.say.hi.common.grpc.GrpcResponseProcess.getResponseProcess;

/**
 * <p>
 * 用户服务gRPC实现
 * </p>
 *
 * @author Bird
 * @since 2025-10-31
 */
@Slf4j
@GrpcService
public class UserServiceGrpcImpl extends UserServiceGrpc.UserServiceImplBase {

    @Autowired
    private UserService userService;

    @Override
    public void register(RegisterRequest request, StreamObserver<RegisterResponse> responseObserver) {
        String invokeName = "UserService.register";
        // 参数校验
        Consumer<RegisterRequest> checkParams = req -> {
            Preconditions.checkArgument(StringUtils.isNotEmpty(req.getUserName()), "invalid userName");
            Preconditions.checkArgument(StringUtils.isNotEmpty(req.getPassword()), "invalid password");
        };

        // 处理逻辑 - 根据开发规范第16条，业务逻辑已下沉到Service层
        GrpcTemplate.ProcessFunc<RegisterRequest, RegisterResponse> processFunc = req -> {
            // 调用业务逻辑 - Service层处理所有业务逻辑并返回RegisterResponse
            return userService.register(req.getUserName(), req.getPassword(), null);
        };

        GrpcTemplate.execute(invokeName, request, checkParams, processFunc,
            getResponseProcess(RegisterResponse.class), responseObserver);
    }
}
