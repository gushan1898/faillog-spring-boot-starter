package com.gushan.service;

import com.gushan.entity.FailLog;
import com.gushan.exception.FailLogException;
import com.gushan.mapper.FailLogMapper;
import com.gushan.properties.FailLogProperties;
import com.gushan.service.impl.FailLogServiceImpl;
import com.gushan.strategy.RetryStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FailLogServiceTest {

    @Mock
    private FailLogMapper failLogMapper;

    @Mock
    private FailLogProperties properties;

    @Mock
    private RetryStrategy retryStrategy;

    private FailLogServiceImpl failLogService;

    @BeforeEach
    void setUp() {
        List<RetryStrategy> strategies = Collections.singletonList(retryStrategy);
        failLogService = new FailLogServiceImpl(failLogMapper, properties, strategies);
        
        when(properties.getMaxRetryCount()).thenReturn(3);
        when(retryStrategy.getStrategyKey()).thenReturn("test_table_test_scene_test_channel");
        when(retryStrategy.retry(any())).thenReturn(true);
        
        failLogService.init();
    }

    @Test
    void save_shouldSucceed_whenValidInput() {
        FailLog failLog = createFailLog();
        when(failLogMapper.insert(any(FailLog.class))).thenReturn(1);
        
        assertDoesNotThrow(() -> failLogService.save(failLog));
        verify(failLogMapper).insert(any(FailLog.class));
    }

    @Test
    void save_shouldThrowException_whenBizNoIsEmpty() {
        FailLog failLog = createFailLog();
        failLog.setBizNo(null);
        
        assertThrows(FailLogException.class, () -> failLogService.save(failLog));
        verify(failLogMapper, never()).insert(any(FailLog.class));
    }

    @Test
    void update_shouldSucceed_whenValidInput() {
        FailLog failLog = createFailLog();
        when(failLogMapper.updateById(any(FailLog.class))).thenReturn(1);
        
        assertDoesNotThrow(() -> failLogService.update(failLog));
        verify(failLogMapper).updateById(any(FailLog.class));
    }

    @Test
    void delete_shouldSucceed_whenValidId() {
        when(failLogMapper.deleteById(anyLong())).thenReturn(1);
        
        assertDoesNotThrow(() -> failLogService.delete(1L));
        verify(failLogMapper).deleteById(1L);
    }

    @Test
    void delete_shouldThrowException_whenIdIsNull() {
        assertThrows(FailLogException.class, () -> failLogService.delete(null));
        verify(failLogMapper, never()).deleteById(any());
    }

    @Test
    void getById_shouldSucceed_whenValidId() {
        FailLog failLog = createFailLog();
        when(failLogMapper.selectById(anyLong())).thenReturn(failLog);
        
        FailLog result = failLogService.getById(1L);
        assertNotNull(result);
        assertEquals(failLog.getBizNo(), result.getBizNo());
    }

    @Test
    void list_shouldReturnAllRecords() {
        FailLog failLog = createFailLog();
        List<FailLog> failLogs = Arrays.asList(failLog, failLog);
        when(failLogMapper.selectList(any())).thenReturn(failLogs);
        
        List<FailLog> result = failLogService.list();
        assertEquals(2, result.size());
    }

    @Test
    void retryFailedLogs_shouldSucceed() {
        // Given
        FailLog failLog = createFailLog();
        when(failLogMapper.selectList(any())).thenReturn(Collections.singletonList(failLog));
        when(failLogMapper.updateById(any())).thenReturn(1);
        
        // When
        failLogService.retryFailedLogs();
        
        // Then
        verify(failLogMapper).selectList(any());
        verify(failLogMapper).updateById(argThat(log -> {
            assertNotNull(log);
            assertTrue(log.getFlag());
            assertEquals("重试成功", log.getRemark());
            return true;
        }));
        verify(retryStrategy).retry(any());
    }

    private FailLog createFailLog() {
        return new FailLog()
                .setBizNo("TEST_001")
                .setBizTable("test_table")
                .setBizScene("test_scene")
                .setBizChannel("test_channel")
                .setRetryCount(0)
                .setFlag(false)
                .setDr(0);
    }
} 