package com.gushan.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.Getter;

/**
 * 失败日志监控指标
 *
 * @author gushan
 */
@Getter
public class FailLogMetrics {
    private final Counter saveCounter;
    private final Counter retryCounter;
    private final Counter retrySuccessCounter;
    private final Counter retryFailureCounter;
    private final Timer saveTimer;
    private final Timer retryTimer;

    public FailLogMetrics(MeterRegistry registry) {
        this.saveCounter = Counter.builder("fail_log_save_total")
                .description("Total number of fail logs saved")
                .register(registry);
        
        this.retryCounter = Counter.builder("fail_log_retry_total")
                .description("Total number of fail log retries")
                .register(registry);
        
        this.retrySuccessCounter = Counter.builder("fail_log_retry_success_total")
                .description("Total number of successful fail log retries")
                .register(registry);
        
        this.retryFailureCounter = Counter.builder("fail_log_retry_failure_total")
                .description("Total number of failed fail log retries")
                .register(registry);
        
        this.saveTimer = Timer.builder("fail_log_save_duration")
                .description("Time taken to save fail logs")
                .register(registry);
        
        this.retryTimer = Timer.builder("fail_log_retry_duration")
                .description("Time taken to retry fail logs")
                .register(registry);
    }
} 