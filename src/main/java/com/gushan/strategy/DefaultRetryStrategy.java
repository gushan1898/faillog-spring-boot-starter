package com.gushan.strategy;

import com.gushan.constant.FailLogConstant;
import com.gushan.entity.FailLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DefaultRetryStrategy implements RetryStrategy {
    @Override
    public boolean retry(FailLog failLog) {
        log.info("使用默认重试策略重试失败日志：{}", failLog);
        return true;  // 确保返回 true 以触发更新
    }

    @Override
    public String getStrategyKey() {
        return FailLogConstant.DEFAULT_STRATEGY_KEY;
    }
} 