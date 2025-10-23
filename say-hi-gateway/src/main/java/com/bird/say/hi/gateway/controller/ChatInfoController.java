package com.bird.say.hi.gateway.controller;

import com.bird.say.hi.gateway.common.Result;
import com.bird.say.hi.gateway.common.ResultUtils;
import com.bird.say.hi.gateway.model.ChatInfo;
import com.bird.say.hi.gateway.controller.request.GetChatInfoRequest;
import com.bird.say.hi.gateway.controller.response.GetChatInfoResponse;
import com.bird.say.hi.gateway.model.Message;
import com.bird.say.hi.gateway.model.MessageType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-24
 */
@RestController
@RequestMapping("/chat-info")
@Tag(name = "聊天信息Controller")
@Slf4j
public class ChatInfoController {

    @Operation(summary = "获取聊天信息")
    @PostMapping("/get")
    public Result<GetChatInfoResponse> getChatInfo(@Valid @RequestBody GetChatInfoRequest request) {
        log.info("getChatInfo request: userId={}, chatId={}, cursor={}, limit={}",
                request.getUserId(), request.getChatId(), request.getCursor(), request.getLimit());

        // TODO: 这里应该调用聊天信息服务获取真实数据
        // 当前先返回模拟数据用于测试
        List<ChatInfo> chatInfos = generateMockChatInfos(request);

        GetChatInfoResponse response = GetChatInfoResponse.builder()
                .chatInfoList(chatInfos)
                .nextCursor(System.currentTimeMillis())
                .hasMore(false)
                .build();

        return ResultUtils.success("获取成功", response);
    }

    /**
     * 生成模拟聊天信息数据（仅用于测试）
     */
    private List<ChatInfo> generateMockChatInfos(GetChatInfoRequest request) {
        List<ChatInfo> chatInfos = new ArrayList<>();
        
        long limit = request.getLimit() != null ? request.getLimit() : 20;
        long startId = request.getCursor() != null ? request.getCursor() : 1L;

        for (int i = 0; i < limit; i++) {
            long chatId = startId + i;
            
            // 创建一个模拟的最后一条消息
            Message lastMessage = Message.builder()
                    .messageId(chatId * 100 + 1)
                    .chatId(chatId)
                    .seqId(chatId * 10)
                    .fromUserId(request.getUserId())
                    .messageType(MessageType.TEXT)
                    .content("这是聊天" + chatId + "的最后一条消息")
                    .showText("最后一条消息")
                    .createTime(System.currentTimeMillis() - i * 1000L)
                    .build();

            ChatInfo chatInfo = ChatInfo.builder()
                    .chatId(chatId)
                    .avatar("https://example.com/avatar/" + chatId + ".jpg")
                    .chatTitle("聊天" + chatId)
                    .readSeqId(chatId * 10 - 5)
                    .maxSeqId(chatId * 10)
                    .lastMessage(lastMessage)
                    .activeTime(System.currentTimeMillis() - i * 60 * 1000L)
                    .unreadCount(ThreadLocalRandom.current().nextInt(10))
                    .priority(ThreadLocalRandom.current().nextInt(100))
                    .build();

            chatInfos.add(chatInfo);
        }

        return chatInfos;
    }
}