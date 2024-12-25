# Fail Log Spring Boot Starter

A Spring Boot starter for handling and retrying failed operations with customizable retry strategies.

## Features

- Automatic logging of failed operations
- Customizable retry strategies
- Scheduled retry mechanism
- Support for multiple business scenarios
- Easy integration with Spring Boot applications

## Quick Start

### 1. Add Dependency

```xml
<dependency>
    <groupId>com.gushan</groupId>
    <artifactId>fail-log-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 2. Configure Properties

```yaml
fail:
  log:
    max-retry-count: 3  # Maximum retry attempts
    enable-liquibase: true  # Enable automatic database schema creation
```

### 3. Basic Usage

```java
@Autowired
private FailLogService failLogService;

public void handleBusinessOperation() {
    try {
        // Your business logic here
        businessService.doSomething();
    } catch (Exception e) {
        // Log the failure
        FailLog failLog = new FailLog()
            .setBizNo("ORDER_001")
            .setBizTable("t_order")
            .setBizScene("payment")
            .setBizChannel("alipay")
            .setRequestData(JsonUtil.toJson(request))
            .setResponseData(JsonUtil.toJson(response));
        
        failLogService.save(failLog);
    }
}
```

## Custom Retry Strategy

### 1. Create a Custom Strategy

```java
@Component
public class OrderRetryStrategy implements RetryStrategy {
    
    @Override
    public boolean retry(FailLog failLog) {
        // Your retry logic here
        return true;
    }
    
    @Override
    public String getStrategyKey() {
        return "t_order_payment_alipay";
    }
}
```

### 2. Register the Strategy

The strategy will be automatically registered when your application starts.

## Configuration Properties

| Property | Description | Default |
|----------|-------------|---------|
| fail.log.max-retry-count | Maximum number of retry attempts | 3 |
| fail.log.enable-liquibase | Enable automatic database schema creation | true |

## Database Schema

The starter automatically creates the following table:

```sql
CREATE TABLE fail_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    biz_no VARCHAR(64) NOT NULL,
    biz_table VARCHAR(64),
    biz_id BIGINT,
    biz_scene VARCHAR(32),
    biz_channel VARCHAR(32),
    request_data CLOB,
    response_data CLOB,
    retry_count INT DEFAULT 0,
    flag BOOLEAN DEFAULT FALSE,
    remark VARCHAR(512),
    request_id VARCHAR(100),
    create_time TIMESTAMP,
    create_person VARCHAR(60),
    update_time TIMESTAMP,
    update_person VARCHAR(60),
    instance_id BIGINT,
    tenant_id BIGINT,
    dr INT DEFAULT 0
);
```

## Advanced Features

### Aspect Logging

The starter provides an aspect for automatic logging of method executions:

```java
@FailLog(
    bizNo = "#order.orderNo",
    bizTable = "t_order",
    bizScene = "payment",
    bizChannel = "alipay"
)
public void processOrder(Order order) {
    // Your business logic
}
```

### Scheduled Retry

Failed operations are automatically retried based on your configuration:

```java
@Scheduled(fixedDelay = 60000)  // Every minute
public void retryFailedLogs() {
    failLogService.retryFailedLogs();
}
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.