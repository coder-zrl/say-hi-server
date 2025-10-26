package com.bird.say.hi.im.message.service;

import com.bird.say.hi.common.utils.ProtoUtils;
import com.bird.say.hi.im.sdk.SendMessageRequest;
import com.bird.say.hi.im.sdk.SendMessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-26
 */
@Service
@Slf4j
public class MessageService {
    public SendMessageResponse sendMessage(SendMessageRequest req) {
        log.info("sendMessage start, request:{}", ProtoUtils.oneLine(req));
        return SendMessageResponse.newBuilder()
                .setErrorCode(1)
                .setErrorMsg("success")
                .build();
    }

}
