package com.bird.say.hi.gateway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-07
 */
@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
@ComponentScan(basePackages = {"com.bird.say.hi.gateway", "com.bird.say.hi.common"})
@MapperScan(basePackages = "com.bird.say.hi.gateway.mapper")
public class SayHiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SayHiGatewayApplication.class, args);
    }

}
