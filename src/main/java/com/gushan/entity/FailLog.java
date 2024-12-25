package com.gushan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 失败日志实体
 *
 * @author gushan
 */
@Data
@Accessors(chain = true)
@TableName("fail_log")
public class FailLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
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
     * 请求数据
     */
    private String requestData;

    /**
     * 响应数据
     */
    private String responseData;

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
     * 请求ID
     */
    private String requestId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createPerson;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private String updatePerson;

    /**
     * 实例ID
     */
    private Long instanceId;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 删除标记
     */
    @TableLogic
    private Integer dr;
} 