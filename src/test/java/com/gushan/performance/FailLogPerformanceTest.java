package com.gushan.performance;

import com.gushan.TestConfiguration;
import com.gushan.entity.FailLog;
import com.gushan.service.FailLogService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest(classes = TestConfiguration.class)
@ActiveProfiles("test")
class FailLogPerformanceTest {

    @Autowired
    private FailLogService failLogService;

    @Test
    @Transactional
    void concurrentSaveTest() throws InterruptedException {
        int threadCount = 10;
        int recordsPerThread = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        List<Long> timeCosts = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            final int threadIndex = i;
            executorService.submit(() -> {
                try {
                    long startTime = System.currentTimeMillis();
                    for (int j = 0; j < recordsPerThread; j++) {
                        failLogService.save(createFailLog(threadIndex + "_" + j));
                    }
                    timeCosts.add(System.currentTimeMillis() - startTime);
                } finally {
                    latch.countDown();
                }
            });
        }

        boolean completed = latch.await(1, TimeUnit.MINUTES);
        assertTrue(completed, "Performance test didn't complete in time");
        
        double avgTime = timeCosts.stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(0.0);
        
        log.info("Average time per thread: {}ms", avgTime);
        log.info("Average time per operation: {}ms", avgTime / recordsPerThread);
    }

    private FailLog createFailLog(String suffix) {
        return new FailLog()
                .setBizNo("PERF_TEST_" + suffix)
                .setBizTable("test_table")
                .setBizScene("test_scene")
                .setBizChannel("test_channel");
    }
} 