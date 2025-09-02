package com.bird.say.hi.server.common.mq;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-09-02
 */
public enum Topic {
    /**
     * 更新会话信息
     */
    UPDATE_SESSION_INFO("update_session_token"),
    ;

    private final String topic;

    Topic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return String.format("topic_%s", topic);
    }
}
