package com.bird.say.hi.gateway.common;

import org.springframework.http.HttpStatus;

/**
 * Result 工具类
 *
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-10-22
 */
public class ResultUtils {

    /**
     * 操作成功
     */
    public static <T> Result<T> success() {
        return Result.success();
    }

    /**
     * 操作成功（带消息）
     */
    public static <T> Result<T> success(String message) {
        return Result.success(message);
    }

    /**
     * 操作成功（带数据）
     */
    public static <T> Result<T> success(T data) {
        return Result.success(data);
    }

    /**
     * 操作成功（带消息和数据）
     */
    public static <T> Result<T> success(String message, T data) {
        return Result.success(message, data);
    }

    /**
     * 操作失败
     */
    public static <T> Result<T> error() {
        return Result.error();
    }

    /**
     * 操作失败（带消息）
     */
    public static <T> Result<T> error(String message) {
        return Result.error(message);
    }

    /**
     * 操作失败（带状态码和消息）
     */
    public static <T> Result<T> error(Integer code, String message) {
        return Result.error(code, message);
    }

    /**
     * 操作失败（带状态码、消息和数据）
     */
    public static <T> Result<T> error(Integer code, String message, T data) {
        return Result.error(code, message, data);
    }

    /**
     * 参数错误
     */
    public static <T> Result<T> badRequest(String message) {
        return Result.error(HttpStatus.BAD_REQUEST.value(), message);
    }

    /**
     * 未授权
     */
    public static <T> Result<T> unauthorized(String message) {
        return Result.error(HttpStatus.UNAUTHORIZED.value(), message);
    }

    /**
     * 禁止访问
     */
    public static <T> Result<T> forbidden(String message) {
        return Result.error(HttpStatus.FORBIDDEN.value(), message);
    }

    /**
     * 资源未找到
     */
    public static <T> Result<T> notFound(String message) {
        return Result.error(HttpStatus.NOT_FOUND.value(), message);
    }

    /**
     * 服务器内部错误
     */
    public static <T> Result<T> internalServerError(String message) {
        return Result.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }

    /**
     * 服务不可用
     */
    public static <T> Result<T> serviceUnavailable(String message) {
        return Result.error(HttpStatus.SERVICE_UNAVAILABLE.value(), message);
    }
}