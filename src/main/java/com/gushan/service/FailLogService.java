package com.gushan.service;

import com.gushan.entity.FailLog;
import java.util.List;

public interface FailLogService {
    void save(FailLog failLog);
    void update(FailLog failLog);
    void delete(Long id);
    FailLog getById(Long id);
    List<FailLog> list();
    void retryFailedLogs();
} 