package com.bird.say.hi.server.im.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 消息表
 * </p>
 *
 * @author Bird
 * @since 2025-09-28
 */
@Getter
@Setter
@Builder
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一消息ID
     */
    @TableId("message_id")
    private Long messageId;

    /**
     * 会话内消息序号，严格单调递增
     */
    private Long seqId;

    /**
     * 会话ID
     */
    private String chatId;

    /**
     * 发送者ID
     */
    private Long senderId;

    /**
     * 消息类型
     */
    private Integer contentType;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 是否被删除:0-未被删除; 1-已被删除
     */
    private Integer deleted;

    /**
     * 创建时间:毫秒时间戳
     */
    private Long createTime;

    /**
     * 更新时间:毫秒时间戳
     */
    private Long updateTime;
}
