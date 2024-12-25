package com.gushan.config;

import com.gushan.mapper.FailLogMapper;
import com.gushan.properties.FailLogProperties;
import com.gushan.service.FailLogService;
import com.gushan.service.impl.FailLogServiceImpl;
import com.gushan.strategy.DefaultRetryStrategy;
import com.gushan.strategy.RetryStrategy;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

/**
 * 失败日志自动配置
 *
 * @author gushan
 */
@Slf4j
@Configuration
@EnableScheduling
@EnableConfigurationProperties(FailLogProperties.class)
@ComponentScan("com.gushan")
@MapperScan("com.gushan.mapper")
public class FailLogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(RetryStrategy.class)
    public RetryStrategy defaultRetryStrategy() {
        return new DefaultRetryStrategy();
    }

    @Bean
    @ConditionalOnMissingBean(FailLogService.class)
    public FailLogService failLogService(FailLogMapper failLogMapper, 
                                       FailLogProperties properties,
                                       List<RetryStrategy> retryStrategies) {
        return new FailLogServiceImpl(failLogMapper, properties, retryStrategies);
    }
} 