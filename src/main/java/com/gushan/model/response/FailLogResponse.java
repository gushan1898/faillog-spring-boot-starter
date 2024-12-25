package com.gushan.model.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 失败日志响应对象
 *
 * @author gushan
 */
@Data
public class FailLogResponse {
    /**
     * ID
     */
    private Long id;

    /**
     * 业务编号
     */
    private String bizNo;

    /**
     * 业务表名
     */
    private String bizTable;

    /**
     * 业务ID
     */
    private Long bizId;

    /**
     * 业务场景
     */
    private String bizScene;

    /**
     * 业务渠道
     */
    private String bizChannel;

    /**
     * 重试次数
     */
    private Integer retryCount;

    /**
     * 成功标识
     */
    private Boolean flag;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 