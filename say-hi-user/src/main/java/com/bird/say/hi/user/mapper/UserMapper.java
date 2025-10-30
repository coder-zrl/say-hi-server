package com.bird.say.hi.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bird.say.hi.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-31
 *
 * 使用MyBatis-Plus的BaseMapper，无需@Mapper注解
 * Spring Boot会自动扫描并注册Mapper Bean
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
