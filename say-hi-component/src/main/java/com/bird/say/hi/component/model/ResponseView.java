package com.bird.say.hi.component.model;

import com.bird.say.hi.component.error.ServiceErrors;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-06
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseView<T> {
    private int code;
    private String message;
    private T data;

    public ResponseView(T data) {
        this.code = ServiceErrors.success.getErrorCode();
        this.message = ServiceErrors.success.getErrorMessage();
        this.data = data;
    }

    public static <T> ResponseView<T> success(T data) {
        return new ResponseView<>(data);
    }

    public static <T> ResponseView<T> success() {
        return new ResponseView<>(null);
    }
}
