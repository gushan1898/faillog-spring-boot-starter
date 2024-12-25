package com.gushan.config;

import com.gushan.metrics.FailLogMetrics;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 监控指标配置
 *
 * @author gushan
 */
@Configuration
public class MetricsConfig {

    @Bean
    public FailLogMetrics failLogMetrics(MeterRegistry registry) {
        return new FailLogMetrics(registry);
    }
} 