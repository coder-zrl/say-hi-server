package com.bird.say.hi.server.im.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bird.say.hi.server.im.entity.Message;
import com.bird.say.hi.server.im.message.service.IMessageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 消息表 前端控制器
 * </p>
 *
 * @author Bird
 * @since 2025-09-27
 */
@RestController("/im/message")
@Tag(name = "消息Controller")
public class MessageController {
    @Resource
    private IMessageService iMessageService;

    @PostMapping("/insert")
    public String insert() {
        boolean save = iMessageService.save(Message.builder()
                .messageId(System.currentTimeMillis())
                .senderId(1L)
                .chatId(UUID.randomUUID().toString())
                .contentType(1)
                .content(JSON.toJSONString(Map.of("name", "Bird")))
                .seqId(System.currentTimeMillis())
                .createTime(System.currentTimeMillis())
                .updateTime(System.currentTimeMillis())
                .build());
        return String.valueOf(save);
    }

    @GetMapping("/list")
    public String list() {
        Page<Message> page = new Page<>(2, 10);
        Page<Message> messagePage = iMessageService.page(page);
        List<Message> records = messagePage.getRecords(); // 获取当前页数据
        long size = messagePage.getSize(); // 每页记录数
        long total = messagePage.getTotal();  // 总记录数
        long pages = messagePage.getPages(); // 总页数
        return JSON.toJSONString(messagePage);
    }
}