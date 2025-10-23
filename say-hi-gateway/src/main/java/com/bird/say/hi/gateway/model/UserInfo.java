package com.bird.say.hi.gateway.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-23
 */
@Data
@Builder
public class UserInfo {
    private Long userId;
    private String nickName;
    private String avatar;
}
