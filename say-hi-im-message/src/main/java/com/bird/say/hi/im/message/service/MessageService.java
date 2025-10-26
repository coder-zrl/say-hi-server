package com.bird.say.hi.im.message.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bird.say.hi.common.utils.ProtoUtils;
import com.bird.say.hi.im.message.entity.Message;
import com.bird.say.hi.im.message.mapper.MessageMapper;
import com.bird.say.hi.im.sdk.DeleteMessageRequest;
import com.bird.say.hi.im.sdk.DeleteMessageResponse;
import com.bird.say.hi.im.sdk.SendMessageRequest;
import com.bird.say.hi.im.sdk.SendMessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-26
 */
@Service
@Slf4j
public class MessageService {
    
    @Resource
    private MessageMapper messageMapper;

    public SendMessageResponse sendMessage(SendMessageRequest req) {
        log.info("sendMessage start, request:{}", ProtoUtils.oneLine(req));
        return SendMessageResponse.newBuilder()
                .setErrorCode(1)
                .setErrorMsg("success")
                .build();
    }
    
    public DeleteMessageResponse deleteMessage(DeleteMessageRequest req) {
        log.info("deleteMessage start, request:{}", ProtoUtils.oneLine(req));
        
        UpdateWrapper<Message> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("chat_id", req.getChatId())
                .eq("seq_id", req.getSeqId())
                .set("deleted", 1)
                .set("update_time", System.currentTimeMillis());
        
        messageMapper.update(null, updateWrapper);
        
        return DeleteMessageResponse.newBuilder()
                .setErrorCode(0)
                .setErrorMsg("success")
                .build();
    }
}