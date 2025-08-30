package com.bird.say.hi.server.common.annotation;

import com.bird.say.hi.server.im.enums.CommandEnum;

import java.lang.annotation.*;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-08-30
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CommandHandler {
    CommandEnum value();
}
