package com.gushan.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 统一响应对象
 *
 * @author gushan
 */
@Data
@Accessors(chain = true)
public class Result<T> {
    /**
     * 状态码
     */
    private int code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    /**
     * 成功
     */
    public static <T> Result<T> success() {
        return new Result<T>()
                .setCode(200)
                .setMessage("success");
    }

    /**
     * 成功
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>()
                .setCode(200)
                .setMessage("success")
                .setData(data);
    }

    /**
     * 失败
     */
    public static <T> Result<T> error(String message) {
        return new Result<T>()
                .setCode(500)
                .setMessage(message);
    }

    /**
     * 失败
     */
    public static <T> Result<T> error(int code, String message) {
        return new Result<T>()
                .setCode(code)
                .setMessage(message);
    }
} 