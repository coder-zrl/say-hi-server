package com.bird.say.hi.gateway.controller.request;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-31
 */
@Data
public class RegisterRequest {
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
