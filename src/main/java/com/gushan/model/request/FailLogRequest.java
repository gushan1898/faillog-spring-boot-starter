package com.gushan.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 失败日志请求对象
 *
 * @author gushan
 */
@Data
public class FailLogRequest {
    /**
     * 业务编号
     */
    @NotBlank(message = "业务编号不能为空")
    private String bizNo;

    /**
     * 业务表名
     */
    @NotBlank(message = "业务表名不能为空")
    private String bizTable;

    /**
     * 业务ID
     */
    private Long bizId;

    /**
     * 业务场景
     */
    @NotBlank(message = "业务场景不能为空")
    private String bizScene;

    /**
     * 业务渠道
     */
    @NotBlank(message = "业务渠道不能为空")
    private String bizChannel;

    /**
     * 请求数据
     */
    private String requestData;

    /**
     * 响应数据
     */
    private String responseData;

    /**
     * 备注
     */
    private String remark;
} 