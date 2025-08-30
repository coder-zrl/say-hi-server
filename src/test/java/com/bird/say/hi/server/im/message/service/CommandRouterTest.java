package com.bird.say.hi.server.im.message.service;

import com.bird.say.hi.server.link.model.LongLinkBody;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-08-30
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommandRouterTest {
    @Autowired
    CommandRouter commandRouter;

    @Test
    void route() {
        LongLinkBody longLinkBody = LongLinkBody.builder()
                .command("Message.Send")
                .data("{\\\"content\\\":\\\"test\\\"}")
                .build();
        commandRouter.route(longLinkBody);
    }
}