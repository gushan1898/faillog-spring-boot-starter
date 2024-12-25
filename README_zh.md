# 失败日志 Spring Boot Starter

[English](README.md) | [中文](README_zh.md)

一个用于处理失败日志和重试机制的 Spring Boot starter 组件。

## 功能特性

- 兼容 Spring Boot 2.x 和 3.x 版本
- 提供失败日志的增删改查操作
- 使用 Liquibase 进行数据库脚本管理（可配置）
- 可配置的重试机制
- 支持多种定时任务类型（如 XXL-Job 等）
- 基于业务表、场景和渠道的策略模式，支持不同的重试逻辑
- 完整的监控指标（基于 Micrometer）
- 异步处理和性能优化
- 全局异常处理
- 完善的日志记录

## 快速开始

1. 添加依赖：

```xml
<dependency>
    <groupId>com.gushan</groupId>
    <artifactId>fail-log-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

2. 配置：

```yaml
fail:
  log:
    enable-liquibase: true  # 是否启用 liquibase
    job-type: xxl-job       # 任务类型：xxl-job
    retry-interval: 5       # 重试间隔（分钟）
    max-retry-count: 3      # 最大重试次数
```

## 使用说明

### 实现自定义重试策略

```java
@Component
public class CustomRetryStrategy implements RetryStrategy {
    @Override
    public String getStrategyKey() {
        return "custom_key";  // 格式：bizTable_bizScene_bizChannel
    }

    @Override
    public boolean retry(FailLog failLog) {
        // 自定义重试逻辑
        return true;
    }
}
```

### 监控指标

系统提供以下监控指标：

- fail_log_save_total: 保存失败日志总数
- fail_log_retry_total: 重试总次数
- fail_log_retry_success_total: 重试成功总数
- fail_log_retry_failure_total: 重试失败总数
- fail_log_save_duration: 保存操作耗时
- fail_log_retry_duration: 重试操作耗时

### 异常处理

系统提供全局异常处理，包括：

- FailLogException: 业务异常（HTTP 400）
- RetryStrategyNotFoundException: 重试策略未找到异常
- RetryFailedException: 重试失败异常
- 其他系统异常（HTTP 500）

### 性能优化

- 使用线程池处理重试任务
- 批量处理能力
- 异步日志记录
- 数据库索引优化

### 工具类

提供以下工具类：

- JsonUtil: JSON 处理工具
- DateUtil: 日期处理工具
- FailLogUtil: 失败日志工具

### 数据库表结构

```sql
create table fail_log (
    id               bigint auto_increment primary key,
    biz_no           varchar(64)  not null comment '业务编号',
    biz_table        varchar(64)  null comment '业务表名',
    biz_id           bigint       null comment '业务id',
    biz_scene        varchar(32)  null comment '业务场景',
    biz_channel      varchar(32)  null comment '业务渠道',
    request_data     json         null comment '入参',
    response_data    json         null comment '出参',
    retry_count      int(4)       default 0  null comment '重试次数',
    flag             tinyint(1)   default 0  null comment '成功标识 0失败 1成功',
    remark           varchar(512) null comment '备注',
    request_id       varchar(100) null comment '请求id',
    create_time      datetime     null comment '创建时间',
    create_person    varchar(60)  null comment '创建人',
    update_time      datetime     null comment '更新时间',
    update_person    varchar(60)  null comment '更新人',
    instance_id      bigint       null comment '实例id',
    tenant_id        bigint       null comment '租户id',
    dr               tinyint      null comment '删除标记'
);
```

### API 接口

```java
public interface FailLogService {
    void save(FailLog failLog);           // 保存失败日志
    void update(FailLog failLog);         // 更新失败日志
    void delete(Long id);                 // 删除失败日志
    FailLog getById(Long id);            // 根据ID获取失败日志
    List<FailLog> list();                // 获取所有失败日志
    void retryFailedLogs();              // 重试失败的日志
}
```

## 测试覆盖

- 单元测试覆盖
- 集成测试
- 性能测试
- 并发测试

## 日志记录

使用 AOP 进行方法调用日志记录，包括：

- 方法调用参数
- 执行时间
- 返回结果
- 异常信息

## 许可证

本项目采用 MIT 许可证。
