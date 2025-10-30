package com.bird.say.hi.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author Bird
 * @since 2025-10-31
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId("user_id")
    private Long userId;

    /**
     * 用户名（唯一）
     */
    private String userName;

    /**
     * 密码（加密存储）
     */
    private String password;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String avatar;

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
