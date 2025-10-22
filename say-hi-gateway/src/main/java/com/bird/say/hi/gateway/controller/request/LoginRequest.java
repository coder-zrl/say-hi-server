package com.bird.say.hi.gateway.controller.request;

import lombok.Data;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-22
 */
@Data
public class LoginRequest {
    private String username;
    private String password;
}
