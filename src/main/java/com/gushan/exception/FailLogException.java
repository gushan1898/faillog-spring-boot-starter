package com.gushan.exception;

/**
 * 失败日志异常
 *
 * @author gushan
 */
public class FailLogException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FailLogException(String message) {
        super(message);
    }

    public FailLogException(String message, Throwable cause) {
        super(message, cause);
    }
} 