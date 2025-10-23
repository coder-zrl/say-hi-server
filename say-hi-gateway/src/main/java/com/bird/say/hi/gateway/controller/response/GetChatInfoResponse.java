package com.bird.say.hi.gateway.controller.response;

import com.bird.say.hi.gateway.model.ChatInfo;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-23
 */
@Data
@Builder
public class GetChatInfoResponse {
    List<ChatInfo> chatInfos;
    boolean hasMore;
}
