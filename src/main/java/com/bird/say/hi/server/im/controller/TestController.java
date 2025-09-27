package com.bird.say.hi.server.im.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.bird.say.hi.server.im.controller.request.TestRequest;
import io.lettuce.core.api.sync.RedisCommands;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-08-30
 */
@RestController
@Slf4j
@Tag(name = "测试Controller")
public class TestController {
    @Autowired
    private RedisCommands<String, String> redisClient;

    @Operation(summary = "普通body请求+Param+Header+Path")
    @Parameters({
            @Parameter(name = "id",description = "文件id",in = ParameterIn.PATH),
            @Parameter(name = "token",description = "请求token",required = true, in = ParameterIn.HEADER),
            @Parameter(name = "name",description = "文件名称",required = true, in=ParameterIn.QUERY)
    })
    @GetMapping("/test")
    public String test(String name) {
        String set = redisClient.set("test", name);
        log.info("set result:{}", set);

        return redisClient.get("test");
    }

    @Operation(summary = "简单测试")
    @GetMapping("/test2")
    public String test2(String name) {
        String set = redisClient.set("test", name);
        log.info("set result:{}", set);

        return redisClient.get("test");
    }

    @Operation(summary = "简单测试@RequestBody")
    @PostMapping("/test3")
    public String test3(@RequestBody TestRequest testRequest) {
        return testRequest.toString();
    }

    @Operation(summary = "测试登录")
    @PostMapping("/login")
    public String login(@RequestBody TestRequest testRequest) {
        StpUtil.login(testRequest.getName());
        return StpUtil.getTokenInfo().toString();
    }

    @Operation(summary = "测试接口需要登录")
    @PostMapping("/needLogin")
    @SaCheckLogin
    public String needLogin(@RequestBody TestRequest testRequest) {
        return "校验通过";
    }
}
