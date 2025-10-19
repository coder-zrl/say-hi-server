package com.bird.say.hi.im.message.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-07
 */
@RestController
public class MessageTestController {
    @GetMapping("/message/test")
    public String test() {
        return "hello world";
    }


}
