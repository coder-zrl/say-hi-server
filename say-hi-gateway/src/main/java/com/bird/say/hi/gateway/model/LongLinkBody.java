package com.bird.say.hi.gateway.model;

import com.bird.say.hi.gateway.LongLinkCommand;
import lombok.Builder;
import lombok.Data;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-09
 */
@Data
@Builder
public class LongLinkBody {
    static class RequestClientInfo {
        public String userId;
        public String appVersion;
    }

    /**
     * 请求的客户端信息
     */
    public RequestClientInfo requestClientInfo;

    /**
     * 长连信令
     */
    public LongLinkCommand command;

    /**
     * 长连的业务数据
     */
    public String data;
}