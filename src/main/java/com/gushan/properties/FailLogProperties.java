package com.gushan.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "fail.log")
public class FailLogProperties {
    private boolean enableLiquibase = true;
    private String jobType = "xxl-job";
    private Integer retryInterval = 5; // 重试间隔（分钟）
    private Integer maxRetryCount = 3; // 最大重试次数
} 