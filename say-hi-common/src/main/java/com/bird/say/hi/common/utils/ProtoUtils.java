package com.bird.say.hi.common.utils;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-07
 */
public class ProtoUtils {
    /**
     * 将 proto格式的 Message 打印为一行输出到日志中
     */
    public static String oneLine(Message message) {
        if (message == null) {
            return StringUtils.EMPTY;
        }
        try {
            return JsonFormat.printer().omittingInsignificantWhitespace().print(message);
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
    }
}
