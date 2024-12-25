package com.gushan.strategy;

import com.gushan.entity.FailLog;

/**
 * 重试策略接口
 *
 * @author gushan
 */
public interface RetryStrategy {
    /**
     * 获取策略key
     *
     * @return 策略key
     */
    String getStrategyKey();

    /**
     * 重试
     *
     * @param failLog 失败日志
     * @return 重试是否成功
     */
    boolean retry(FailLog failLog);
} 