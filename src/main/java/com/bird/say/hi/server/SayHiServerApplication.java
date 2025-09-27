package com.bird.say.hi.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-08-30
 */
@SpringBootApplication
/**
 * 需要指定具体路径，否则可能会导致接口被注册为bean，beanName和实现类相同
 */
@MapperScan("com.bird.say.hi.server.im.message.mapper")
public class SayHiServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SayHiServerApplication.class, args);
	}

}
