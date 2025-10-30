package com.bird.say.hi.gateway.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.bird.say.hi.gateway.common.Result;
import com.bird.say.hi.gateway.common.ResultUtils;
import com.bird.say.hi.gateway.controller.request.LoginRequest;
import com.bird.say.hi.gateway.controller.request.RegisterRequest;
import com.bird.say.hi.gateway.controller.response.LoginResponse;
import com.bird.say.hi.gateway.controller.response.RegisterResponse;
import com.bird.say.hi.gateway.model.UserInfo;
import com.bird.say.hi.gateway.service.UserGrpcService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-22
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户Controller")
public class UserController {
    @Resource
    private UserGrpcService userGrpcService;

    @Operation(summary = "测试用户登录")
    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest request) {
        StpUtil.login(request.getUsername());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        LoginResponse response = LoginResponse.builder()
                .tokenInfo(tokenInfo)
                .userInfo(UserInfo.builder()
                        .userId(111L)
                        .nickName("黑胡")
                        .avatar("https://img1.baidu.com/it/u=3357071773,1618494340&fm=253&app=138&f=JPEG")
                        .build())
                .build();

        return ResultUtils.success("登录成功", response);
    }

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = userGrpcService.register(request);
        // 使用Sa-Token登录
        StpUtil.login(response.getUserInfo().getUserId());
        return ResultUtils.success("注册成功", response);
    }
}
