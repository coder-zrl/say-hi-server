package com.bird.say.hi.gateway.controller;

import cn.hutool.json.JSONUtil;
import com.bird.say.hi.gateway.common.Result;
import com.bird.say.hi.gateway.common.ResultUtils;
import com.bird.say.hi.gateway.controller.request.PullMessageRequest;
import com.bird.say.hi.gateway.controller.response.PullMessageResponse;
import com.bird.say.hi.gateway.model.Image;
import com.bird.say.hi.gateway.model.Message;
import com.bird.say.hi.gateway.model.MessageType;
import com.bird.say.hi.gateway.controller.request.SendMessageRequest;
import com.bird.say.hi.gateway.controller.response.SendMessageResponse;
import com.bird.say.hi.gateway.model.Text;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-23
 */
@RestController
@RequestMapping("/message")
@Tag(name = "消息Controller")
@Slf4j
public class MessageController {

    @Operation(summary = "拉取历史消息，从cursor往前拉")
    @GetMapping("/pullHistoryMessage")
    public Result<PullMessageResponse> pullMessage(@Valid PullMessageRequest request) {
        log.info("pullMessage request: channelId={}, cursor={}, limit={}",
                request.getChatId(), request.getCursor(), request.getLimit());

        // TODO: 这里应该调用消息服务获取真实数据
        // 当前先返回模拟数据用于测试
        List<Message> messages = generateMockMessages(request);

        // 计算下一页游标
        Long nextCursor = null;
        Boolean hasMore = false;
        if (!messages.isEmpty()) {
            Message lastMessage = messages.get(messages.size() - 1);
            nextCursor = lastMessage.getSeqId();
            // 简单判断是否还有更多数据（实际应该查询数据库判断）
            hasMore = messages.size() >= request.getLimit();
        }

        PullMessageResponse response = PullMessageResponse.builder()
                .messages(messages)
                .nextCursor(nextCursor)
                .hasMore(hasMore)
                .build();

        return ResultUtils.success("拉取成功", response);
    }

    @Operation(summary = "发送消息")
    @PostMapping("/send")
    public Result<SendMessageResponse> sendMessage(@Valid @RequestBody SendMessageRequest request) {
        log.info("sendMessage request: userId={}", request.getUserId());

        // TODO: 这里应该调用消息服务发送真实消息
        // 当前先返回模拟数据用于测试

        SendMessageResponse response = SendMessageResponse.builder()
                .errorCode(0)
                .errorMsg("success")
                .build();

        return ResultUtils.success("发送成功", response);
    }

    /**
     * 生成模拟消息数据（仅用于测试）
     */
    private List<Message> generateMockMessages(PullMessageRequest request) {
        List<Message> messages = new ArrayList<>();
        Random random = new Random();

        // 从游标开始生成消息
        long startSeqId = request.getCursor() != null ? request.getCursor() : 1L;

        for (int i = 0; i < request.getLimit(); i++) {
            long seqId = startSeqId + i;
            MessageType[] types = {MessageType.TEXT, MessageType.IMAGE};
            MessageType type = types[random.nextInt(types.length)];

            Message message = Message.builder()
                    .messageId(System.currentTimeMillis() + seqId)
                    .chatId(request.getChatId())
                    .seqId(seqId)
                    .fromUserId(100L + seqId % 5) // 模拟5个不同用户
                    .messageType(type)
                    .content(generateMockContent(type, i))
                    .showText(generateMockShowText(type, i))
                    .expireTime(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000L) // 7天后过期
                    .createTime(System.currentTimeMillis() - (request.getLimit() - i) * 1000L) // 递增时间
                    .updateTime(System.currentTimeMillis() - (request.getLimit() - i) * 1000L)
                    .deleteTime(null)
                    .build();

            messages.add(message);
        }

        return messages;
    }

    private String generateMockContent(MessageType type, int index) {
        switch (type) {
            case TEXT:
                return JSONUtil.toJsonStr(Text.builder().text("这是第" + (index + 1) + "条文本消息内容"));
            case IMAGE:
                return JSONUtil.toJsonStr(Image.builder().url(""));
            default:
                return "未知消息类型";
        }
    }

    private String generateMockShowText(MessageType type, int index) {
        switch (type) {
            case TEXT:
                return "这是第" + (index + 1) + "条文本消息";
            case IMAGE:
                return "[图片]";
            default:
                return "[未知消息]";
        }
    }
}