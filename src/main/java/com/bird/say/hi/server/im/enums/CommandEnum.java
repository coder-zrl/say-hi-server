package com.bird.say.hi.server.im.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 映射为端上的长连命令
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-08-30
 */
public enum CommandEnum {
    /**
     * 发送消息
     */
    MESSAGE_SEND("Message.Send")
    ;

    private String command;

    CommandEnum(String command) {
        this.command = command;
    }

    // 缓存command到枚举的映射
    private static final Map<String, CommandEnum> COMMAND_MAP = new HashMap<>();

    static {
        for (CommandEnum value : values()) {
            COMMAND_MAP.put(value.command, value);
        }
    }

    public static CommandEnum fromCommand(String command) {
        return COMMAND_MAP.get(command);
    }
}
