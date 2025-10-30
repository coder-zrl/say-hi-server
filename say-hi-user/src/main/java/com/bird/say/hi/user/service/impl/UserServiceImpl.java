package com.bird.say.hi.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bird.say.hi.im.sdk.RegisterResponse;
import com.bird.say.hi.im.sdk.UserInfo;
import com.bird.say.hi.sdk.common.ErrorCode;
import com.bird.say.hi.user.entity.User;
import com.bird.say.hi.user.mapper.UserMapper;
import com.bird.say.hi.user.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.Instant;

/**
 * <p>
 * 用户服务实现
 * </p>
 *
 * @author Bird
 * @since 2025-10-31
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    @Override
    public RegisterResponse register(String userName, String password, String nickName) {
        // 检查用户名是否已存在 - 使用MyBatis-Plus的QueryWrapper
        User existingUser = userMapper.selectOne(new LambdaQueryWrapper<User>()
            .eq(User::getUserName, userName)
            .eq(User::getDeleted, 0));

        if (existingUser != null) {
            // 使用error_code.proto中的标准错误码
            log.info("用户注册失败: userName={}, 原因: 用户名已存在", userName);
            return RegisterResponse.newBuilder()
                .setErrorCode(ErrorCode.ILLEGAL_PARAMETER)
                .setErrorMsg("用户名已存在")
                .setUserId(0)
                .setUserInfo(UserInfo.getDefaultInstance())
                .build();
        }

        // 创建新用户
        User user = new User();
        user.setUserName(userName);
        // 密码加密存储
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        user.setNickName(nickName != null ? nickName : userName);
        user.setAvatar(""); // 默认头像为空
        user.setDeleted(0);
        long now = Instant.now().toEpochMilli();
        user.setCreateTime(now);
        user.setUpdateTime(now);

        // 插入数据库 - 使用MyBatis-Plus的insert方法
        userMapper.insert(user);

        log.info("用户注册成功: userName={}, userId={}", userName, user.getUserId());

        // 成功响应
        return RegisterResponse.newBuilder()
            .setErrorCode(ErrorCode.SUCCESS)
            .setErrorMsg("")
            .setUserId(user.getUserId())
            .setUserInfo(UserInfo.newBuilder()
                .setUserId(user.getUserId())
                .setNickName(user.getNickName())
                .setAvatar(user.getAvatar() != null ? user.getAvatar() : "")
                .build())
            .build();
    }

    @Override
    public User getByUserName(String userName) {
        // 使用MyBatis-Plus的QueryWrapper
        return userMapper.selectOne(new LambdaQueryWrapper<User>()
            .eq(User::getUserName, userName)
            .eq(User::getDeleted, 0));
    }

    @Override
    public User getByUserId(Long userId) {
        // 使用MyBatis-Plus的selectById方法
        return userMapper.selectById(userId);
    }
}
