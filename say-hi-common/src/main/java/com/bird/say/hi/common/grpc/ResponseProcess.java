package com.bird.say.hi.common.grpc;

import com.google.protobuf.GeneratedMessageV3;

/**
 * 处理请求失败时返回错误码，拼接错误信息等，用于服务可用性统计
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-12
 */
public interface ResponseProcess<Resp extends GeneratedMessageV3> {
    /**
     * 生成response
     *
     * @param errorCode 错误码
     * @param errorMsg 错误信息
     * @return response
     */
    Resp genResponse(int errorCode, String errorMsg);

    /**
     * 获取错误码
     *
     * @param resp response
     * @return 错误码
     */
    int getErrorCode(Resp resp);

    /**
     * 获取错误信息
     *
     * @param resp response
     * @return 错误信息
     */
    String getErrorMsg(Resp resp);
}
