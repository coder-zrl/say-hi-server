package com.bird.say.hi.gateway.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-24
 */
@Data
@Builder
public class ChatInfo {
    /**
     * 会话ID
     */
    private Long chatId;

    /**
     * 会话头像
     */
    private String avatar;

    /**
     * 会话标题
     */
    private String chatTitle;

    /**
     * 已读消息ID
     */
    private Long readSeqId;

    /**
     * 最大消息ID
     */
    private Long maxSeqId;

    /**
     * 最后一条消息
     */
    private Message lastMessage;

    /**
     * 活跃时间
     */
    private Long activeTime;

    /**
     * 未读数数量
     */
    private Integer unreadCount;

    /**
     * 会话优先级，优先级的数值越大，优先级越高
     */
    private Integer priority;
}