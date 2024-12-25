package com.gushan.exception;

/**
 * 重试失败异常
 *
 * @author gushan
 */
public class RetryFailedException extends FailLogException {
    private static final long serialVersionUID = 1L;

    public RetryFailedException(String message) {
        super(message);
    }

    public RetryFailedException(String message, Throwable cause) {
        super(message, cause);
    }
} 