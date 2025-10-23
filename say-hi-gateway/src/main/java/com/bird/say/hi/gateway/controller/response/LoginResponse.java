package com.bird.say.hi.gateway.controller.response;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.bird.say.hi.gateway.model.UserInfo;
import lombok.Builder;
import lombok.Data;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-23
 */
@Data
@Builder
public class LoginResponse {
    UserInfo userInfo;
    SaTokenInfo tokenInfo;
}
