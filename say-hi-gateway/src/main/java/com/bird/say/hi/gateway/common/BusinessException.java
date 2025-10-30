package com.bird.say.hi.gateway.common;

/**
 * 业务异常
 *
 * @author Bird
 * @since 2025-10-31
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
