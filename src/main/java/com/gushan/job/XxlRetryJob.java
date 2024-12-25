package com.gushan.job;

import com.gushan.service.FailLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class XxlRetryJob implements RetryJob {
    private final FailLogService failLogService;

    @Override
    public void execute() {
        failLogService.retryFailedLogs();
    }
} 