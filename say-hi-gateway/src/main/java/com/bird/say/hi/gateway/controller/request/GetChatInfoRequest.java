package com.bird.say.hi.gateway.controller.request;

import lombok.Builder;
import lombok.Data;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-24
 */
@Data
@Builder
public class GetChatInfoRequest {
    private Long userId;
    private Long chatId;
    private Long cursor;
    private Integer limit;
}