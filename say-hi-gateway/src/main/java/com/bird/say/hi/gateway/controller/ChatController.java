package com.bird.say.hi.gateway.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.bird.say.hi.gateway.common.Result;
import com.bird.say.hi.gateway.controller.response.GetChatInfoResponse;
import com.bird.say.hi.gateway.model.ChatInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utils.RandomPersonInfoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-23
 */
@RestController
@RequestMapping("/chat")
@Tag(name = "会话Controller")
public class ChatController {

    @SaCheckLogin
    @GetMapping("/list")
    @Operation(summary = "拉取会话列表", description = "根据时间戳拉取活跃时间超过指定水位的会话列表")
    public Result<GetChatInfoResponse> getChatInfo(
            @Parameter(description = "时间戳，表示会话活跃时间水位线", required = true)
            @RequestParam Long timestamp,
            @Parameter(description = "返回数量限制，默认20", example = "20")
            @RequestParam(defaultValue = "20") Integer limit) {

        // 模拟数据，实际应该从数据库或缓存中查询
        List<ChatInfo> result = new ArrayList<>();

        // 模拟创建一些测试数据
        long currentTime = System.currentTimeMillis();
        for (int i = 0; i < limit; i++) {
            ChatInfo chatInfo = ChatInfo.builder()
                    .chatId("session_" + i)
                    .chatTitle(RandomPersonInfoUtil.getRandomChineseName())
                    .lastActiveTime(currentTime - i * 12 * 60 * 60 * 1000L)
                    .unreadCount(i * 5)
                    .readSeqId(0L)
                    .maxSeqId(i * 10L)
                    .lastMessageContent("这是第" + i + "条消息内容")
                    .lastMessageTime(currentTime - i * 12 * 60 * 60 * 1000L)
                    .priority(ThreadLocalRandom.current().nextInt(100))
                    .stickyTop(i % 2 == 0)
                    .avatar("https://img1.baidu.com/it/u=3357071773,1618494340&fm=253&app=138&f=JPEG")
                    .build();
            result.add(chatInfo);
        }

        GetChatInfoResponse response = GetChatInfoResponse.builder()
                .chatInfos(result)
                .hasMore(false)
                .build();

        return Result.success("拉取会话列表成功", response);
    }
}
