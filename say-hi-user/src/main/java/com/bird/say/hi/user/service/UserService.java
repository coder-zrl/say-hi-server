package com.bird.say.hi.user.service;

import com.bird.say.hi.im.sdk.RegisterResponse;
import com.bird.say.hi.user.entity.User;

/**
 * <p>
 * 用户服务接口
 * </p>
 *
 * @author Bird
 * @since 2025-10-31
 */
public interface UserService {

    /**
     * 用户注册
     *
     * @param userName 用户名
     * @param password 密码
     * @param nickName 昵称
     * @return 注册响应，包含错误码和用户信息
     */
    RegisterResponse register(String userName, String password, String nickName);

    /**
     * 根据用户名查询用户
     *
     * @param userName 用户名
     * @return 用户信息
     */
    User getByUserName(String userName);

    /**
     * 根据用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    User getByUserId(Long userId);
}
