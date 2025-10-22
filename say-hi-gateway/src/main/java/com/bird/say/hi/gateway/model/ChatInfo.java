package com.bird.say.hi.gateway.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-23
 */
@Data
@Builder
public class ChatInfo {
    /**
     * 会话头像
     */
    private String avatar;

    /**
     * 会话ID
     */
    private String chatId;

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
     * 最后一条消息的时间
     */
    private Long lastMessageTime;

    /**
     * 最后一条消息内容
     */
    private String lastMessageContent;

    /**
     * 最后活跃时间戳
     */
    private Long lastActiveTime;

    /**
     * 未读数数量
     */
    private Integer unreadCount;

    /**
     * 会话是否置顶
     */
    private Boolean stickyTop;

    /**
     * 会话优先级，按优先级降序排列
     */
    private Integer priority;
}
