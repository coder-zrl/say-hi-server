package com.bird.say.hi.gateway.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-24
 */
@Data
@Builder
public class Text {
    private String text;
}