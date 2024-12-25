package com.gushan.exception;

/**
 * 重试策略未找到异常
 *
 * @author gushan
 */
public class RetryStrategyNotFoundException extends FailLogException {
    private static final long serialVersionUID = 1L;

    public RetryStrategyNotFoundException(String message) {
        super(message);
    }
} 