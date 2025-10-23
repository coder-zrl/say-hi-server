package com.bird.say.hi.gateway.controller.response;

import lombok.Builder;
import lombok.Data;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-24
 */
@Data
@Builder
public class SendMessageResponse {
    private Integer errorCode;
    private String errorMsg;
}