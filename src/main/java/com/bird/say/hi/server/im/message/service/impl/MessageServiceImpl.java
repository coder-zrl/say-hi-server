package com.bird.say.hi.server.im.message.service.impl;

import com.bird.say.hi.server.im.entity.Message;
import com.bird.say.hi.server.im.message.mapper.MessageMapper;
import com.bird.say.hi.server.im.message.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author Bird
 * @since 2025-09-28
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

}
