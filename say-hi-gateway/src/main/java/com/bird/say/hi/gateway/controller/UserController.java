package com.bird.say.hi.gateway.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.bird.say.hi.gateway.common.Result;
import com.bird.say.hi.gateway.common.ResultUtils;
import com.bird.say.hi.gateway.controller.request.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @Operation(summary = "测试用户登录")
    @PostMapping("/login")
    public Result<SaTokenInfo> login(@RequestBody LoginRequest request) {
        StpUtil.login(request.getUsername());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return ResultUtils.success("登录成功", tokenInfo);
    }
}
