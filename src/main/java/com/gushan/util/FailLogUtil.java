package com.gushan.util;

import com.gushan.entity.FailLog;
import com.gushan.exception.FailLogException;
import org.springframework.util.StringUtils;

/**
 * 失败日志工具类
 *
 * @author gushan
 */
public class FailLogUtil {

    private FailLogUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 校验失败日志
     *
     * @param failLog 失败日志
     */
    public static void validateFailLog(FailLog failLog) {
        if (failLog == null) {
            throw new FailLogException("失败日志不能为空");
        }
        if (!StringUtils.hasText(failLog.getBizNo())) {
            throw new FailLogException("业务编号不能为空");
        }
        if (!StringUtils.hasText(failLog.getBizTable())) {
            throw new FailLogException("业务表名不能为空");
        }
        if (!StringUtils.hasText(failLog.getBizScene())) {
            throw new FailLogException("业务场景不能为空");
        }
        if (!StringUtils.hasText(failLog.getBizChannel())) {
            throw new FailLogException("业务渠道不能为空");
        }
    }

    /**
     * 获取策略key
     *
     * @param failLog 失败日志
     * @return 策略key
     */
    public static String getStrategyKey(FailLog failLog) {
        validateFailLog(failLog);
        return String.format("%s_%s_%s", 
            failLog.getBizTable(), 
            failLog.getBizScene(), 
            failLog.getBizChannel());
    }
} 