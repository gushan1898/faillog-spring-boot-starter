package com.gushan.integration;

import com.gushan.TestConfiguration;
import com.gushan.entity.FailLog;
import com.gushan.service.FailLogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestConfiguration.class)
@ActiveProfiles("test")
class FailLogIntegrationTest {
    @Autowired
    private FailLogService failLogService;

    @Test
    @Transactional
    void testSaveAndGet() {
        // 创建测试数据
        FailLog failLog = new FailLog()
                .setBizNo("TEST_001")
                .setBizTable("test_table")
                .setBizScene("test_scene")
                .setBizChannel("test_channel");

        // 保存
        failLogService.save(failLog);

        // 查询并验证
        FailLog saved = failLogService.getById(failLog.getId());
        assertNotNull(saved);
        assertEquals("TEST_001", saved.getBizNo());
        assertEquals("test_table", saved.getBizTable());
        assertEquals("test_scene", saved.getBizScene());
        assertEquals("test_channel", saved.getBizChannel());
    }
} 