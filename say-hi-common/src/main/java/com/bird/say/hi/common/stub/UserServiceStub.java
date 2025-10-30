package com.bird.say.hi.common.stub;

import com.bird.say.hi.im.sdk.RegisterRequest;
import com.bird.say.hi.im.sdk.RegisterResponse;
import com.bird.say.hi.im.sdk.UserServiceGrpc;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import static com.bird.say.hi.sdk.common.ErrorCode.SYSTEM_BUSY;

/**
 * <p>
 * User服务Stub封装类
 * 封装用户服务相关的gRPC调用，其他模块可通过此类调用用户服务
 * </p>
 *
 * @author Bird
 * @since 2025-10-31
 */
@Service
@Slf4j
public class UserServiceStub {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserServiceStub.class);

    @GrpcClient("say-hi-user")
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    /**
     * 用户注册
     *
     * @param userName 用户名
     * @param password 密码
     * @param nickName 昵称（可选）
     * @return 注册响应，包含错误码和用户信息
     */
    @Nullable
    public RegisterResponse register(String userName, String password, String nickName) {
        RegisterRequest request = RegisterRequest.newBuilder()
                .setUserName(userName)
                .setPassword(password)
                .build();
        try {
            return userServiceBlockingStub.register(request);
        } catch (Exception e) {
            log.warn("用户注册失败 userName={}, nickName={}, error={}", userName, nickName, e.getMessage(), e);
        }
        return null;
    }
}
