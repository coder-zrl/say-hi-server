package com.bird.say.hi.gateway.controller.response;

import com.bird.say.hi.gateway.model.UserInfo;
import lombok.Builder;
import lombok.Data;

import cn.dev33.satoken.stp.SaTokenInfo;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-31
 */
@Data
@Builder
public class RegisterResponse {
    private SaTokenInfo tokenInfo;
    private UserInfo userInfo;
}
