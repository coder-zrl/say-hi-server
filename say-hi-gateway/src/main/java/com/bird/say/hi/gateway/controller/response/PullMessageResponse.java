package com.bird.say.hi.gateway.controller.response;

import com.bird.say.hi.gateway.model.Message;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-23
 */
@Data
@Builder
public class PullMessageResponse {
    /**
     * 消息列表
     */
    private List<Message> messages;

    /**
     * 下一次拉取的游标
     */
    private Long nextCursor;

    /**
     * 是否还有更多消息
     */
    private Boolean hasMore;
}