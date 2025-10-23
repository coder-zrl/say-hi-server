package com.bird.say.hi.gateway.controller.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-23
 */
@Data
public class PullMessageRequest {
    /**
     * 会话Id
     */
    @NotNull(message = "会话Id不能为空")
    private Long chatId;

    /**
     * 游标，用于分页拉取消息
     */
    private Long cursor;

    /**
     * 拉取消息数量限制
     */
    @Min(value = 1, message = "拉取数量至少为1")
    @Max(value = 100, message = "拉取数量不能超过100")
    private Integer limit = 20;
}