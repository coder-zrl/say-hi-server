package com.bird.say.hi.gateway;

import lombok.Data;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-24
 */
public enum LongLinkCommand {
    MESSAGE_SEND("Message.Send"),
    MESSAGE_PUSH("Message.Push"),
    ;

    String command;

    LongLinkCommand(String command) {
        this.command = command;
    }
}
