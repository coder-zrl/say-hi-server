package com.bird.say.hi.gateway.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-24
 */
@Data
@Builder
public class Message {
    /**
     * 全局唯一消息Id
     */
    private Long messageId;

    /**
     * 会话Id
     */
    private Long chatId;

    /**
     * 会话维度递增趋势的Id，用于消息排序
     */
    private Long seqId;

    /**
     * 发送者Id
     */
    private Long fromUserId;

    /**
     * 消息类型
     */
    private MessageType messageType;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息在会话列表中展示内容
     */
    private String showText;

    /**
     * 接收者Id列表
     */
    private List<Long> receiverId;

    /**
     * 消息过期时间
     */
    private Long expireTime;

    /**
     * 消息创建时间
     */
    private Long createTime;

    /**
     * 消息更新时间
     */
    private Long updateTime;

    /**
     * 消息删除时间
     */
    private Long deleteTime;
}