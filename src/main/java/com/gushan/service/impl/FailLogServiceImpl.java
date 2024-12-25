package com.gushan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gushan.constant.FailLogConstant;
import com.gushan.entity.FailLog;
import com.gushan.exception.FailLogException;
import com.gushan.mapper.FailLogMapper;
import com.gushan.properties.FailLogProperties;
import com.gushan.service.FailLogService;
import com.gushan.strategy.RetryStrategy;
import com.gushan.util.FailLogUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 失败日志服务实现
 *
 * @author gushan
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FailLogServiceImpl implements FailLogService {
    private final FailLogMapper failLogMapper;
    private final FailLogProperties properties;
    private final List<RetryStrategy> retryStrategies;
    private final Map<String, RetryStrategy> strategyMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        retryStrategies.forEach(strategy -> {
            String key = strategy.getStrategyKey();
            log.info("注册重试策略：{} -> {}", key, strategy.getClass().getSimpleName());
            strategyMap.put(key, strategy);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(FailLog failLog) {
        FailLogUtil.validateFailLog(failLog);
        failLog.setCreateTime(LocalDateTime.now())
               .setUpdateTime(LocalDateTime.now())
               .setRetryCount(0)
               .setFlag(false)
               .setDr(0);
        failLogMapper.insert(failLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(FailLog failLog) {
        failLog.setUpdateTime(LocalDateTime.now());
        failLogMapper.updateById(failLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        if (id == null) {
            throw new FailLogException("ID不能为空");
        }
        failLogMapper.deleteById(id);
    }

    @Override
    public FailLog getById(Long id) {
        if (id == null) {
            throw new FailLogException("ID不能为空");
        }
        return failLogMapper.selectById(id);
    }

    @Override
    public List<FailLog> list() {
        return failLogMapper.selectList(
            new LambdaQueryWrapper<FailLog>()
                .eq(FailLog::getDr, 0)
                .orderByDesc(FailLog::getCreateTime)
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void retryFailedLogs() {
        LambdaQueryWrapper<FailLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FailLog::getFlag, false)
               .eq(FailLog::getDr, 0)
               .lt(FailLog::getRetryCount, properties.getMaxRetryCount())
               .orderByAsc(FailLog::getCreateTime);
        
        List<FailLog> failLogs = failLogMapper.selectList(wrapper);
        log.info("找到{}条需要重试的失败日志", failLogs.size());
        
        for (FailLog failLog : failLogs) {
            try {
                String strategyKey = getStrategyKey(failLog);
                RetryStrategy strategy = strategyMap.get(strategyKey);
                
                if (strategy == null) {
                    strategy = strategyMap.get(FailLogConstant.DEFAULT_STRATEGY_KEY);
                    log.info("未找到对应策略[{}]，使用默认策略", strategyKey);
                }
                
                if (strategy == null) {
                    log.warn("未找到重试策略，strategyKey: {}", strategyKey);
                    continue;
                }

                log.debug("使用策略[{}]重试失败日志：{}", strategyKey, failLog);
                boolean retryResult = strategy.retry(failLog);
                log.debug("重试结果：{}", retryResult);

                if (retryResult) {
                    failLog.setFlag(true)
                           .setRemark("重试成功")
                           .setUpdateTime(LocalDateTime.now());
                } else {
                    failLog.setRetryCount(failLog.getRetryCount() + 1)
                           .setRemark("重试失败，当前重试次数：" + failLog.getRetryCount())
                           .setUpdateTime(LocalDateTime.now());
                }
                failLogMapper.updateById(failLog);
                log.info("更新失败日志状态：{}", failLog);
            } catch (Exception e) {
                log.error("重试失败，failLog: {}", failLog, e);
                failLog.setRetryCount(failLog.getRetryCount() + 1)
                       .setRemark("重试异常：" + e.getMessage())
                       .setUpdateTime(LocalDateTime.now());
                failLogMapper.updateById(failLog);
            }
        }
    }

    private String getStrategyKey(FailLog failLog) {
        return String.format("%s_%s_%s", 
            failLog.getBizTable(), 
            failLog.getBizScene(), 
            failLog.getBizChannel());
    }
} 