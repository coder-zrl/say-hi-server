package com.bird.say.hi.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * <p>
 * 用户服务启动类
 * 直接使用MyBatis-Plus的BaseMapper，无需@MapperScan注解
 * </p>
 *
 * @author Bird
 * @since 2025-10-31
 */
@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
