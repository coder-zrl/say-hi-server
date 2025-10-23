package com.bird.say.hi.gateway.model;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-23
 */
public enum MessageType {
    TEXT(0, "文本"),
    IMAGE(1, "图片"),
    VIDEO(2, "视频"),
    ;

    private final int code;
    private final String description;

    MessageType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static MessageType fromCode(int code) {
        for (MessageType type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown message type code: " + code);
    }
}