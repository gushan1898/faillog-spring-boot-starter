# Fail Log Spring Boot Starter

[English](README.md) | [中文](README_zh.md)

A Spring Boot starter for handling failure logs and retry mechanisms.

## Features

- Compatible with Spring Boot 2.x and 3.x
- CRUD operations for failure logs
- Database schema management with Liquibase (configurable)
- Configurable retry mechanism
- Support for multiple job types (XXL-Job, etc.)
- Strategy pattern for different retry scenarios

## Quick Start

1. Add dependency:
xml
<dependency>
<groupId>com.gushan</groupId>
<artifactId>fail-log-spring-boot-starter</artifactId>
<version>1.0.0</version>
</dependency>

2. Configuration:

```yaml
fail:
  log:
    enable-liquibase: true
    job-type: xxl-job
    retry-interval: 5
    max-retry-count: 3
```

## Usage

Implement your own retry strategy:
