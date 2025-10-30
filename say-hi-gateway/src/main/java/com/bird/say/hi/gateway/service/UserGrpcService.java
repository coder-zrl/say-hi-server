package com.bird.say.hi.gateway.service;

import com.bird.say.hi.common.stub.UserServiceStub;
import com.bird.say.hi.gateway.controller.request.RegisterRequest;
import com.bird.say.hi.gateway.controller.response.RegisterResponse;
import com.bird.say.hi.gateway.model.UserInfo;
import com.bird.say.hi.gateway.common.BusinessException;
import com.bird.say.hi.sdk.common.ErrorCode;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import cn.dev33.satoken.stp.StpUtil;

/**
 * <p>
 * User gRPC客户端服务
 * 使用common模块的UserServiceStub封装，简化调用逻辑
 * </p>
 *
 * @author Bird
 * @since 2025-10-31
 */
@Slf4j
@Service
public class UserGrpcService {
    @Resource
    private UserServiceStub userServiceStub;

    /**
     * 用户注册
     *
     * @param request 注册请求
     * @return 注册响应
     */
    public RegisterResponse register(RegisterRequest request) {
        // 使用UserServiceStub调用用户服务
        com.bird.say.hi.im.sdk.RegisterResponse register = userServiceStub.register(request.getUsername(), request.getPassword(), null);

        // 检查错误码，使用error_code.proto中的定义
        if (register == null || register.getErrorCode() != ErrorCode.SUCCESS) {
            throw new BusinessException("用户注册失败");
        }

        // 转换响应
        UserInfo userInfo = UserInfo.builder()
            .userId(register.getUserId())
            .nickName(register.getUserInfo().getNickName())
            .avatar(register.getUserInfo().getAvatar())
            .build();

        return RegisterResponse.builder()
            .tokenInfo(StpUtil.getTokenInfo())
            .userInfo(userInfo)
            .build();
    }
}
