package com.gushan.constant;

/**
 * 失败日志常量
 *
 * @author gushan
 */
public interface FailLogConstant {
    /**
     * 默认策略key
     */
    String DEFAULT_STRATEGY_KEY = "default";
    
    /**
     * 默认任务类型
     */
    String DEFAULT_JOB_TYPE = "xxl-job";
    
    /**
     * 默认重试间隔（分钟）
     */
    int DEFAULT_RETRY_INTERVAL = 5;
    
    /**
     * 默认最大重试次数
     */
    int DEFAULT_MAX_RETRY_COUNT = 3;
} 