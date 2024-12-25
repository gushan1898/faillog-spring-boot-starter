package com.gushan.aspect;

import com.gushan.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 日志切面
 *
 * @author gushan
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(* com.gushan.service..*.*(..))")
    public void serviceLog() {}

    @Around("serviceLog()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        Object[] args = point.getArgs();

        log.info("开始执行 {}.{}, 参数: {}", className, methodName, JsonUtil.toJson(args));
        long startTime = System.currentTimeMillis();

        try {
            Object result = point.proceed();
            log.info("执行完成 {}.{}, 耗时: {}ms, 返回值: {}", 
                className, methodName, 
                System.currentTimeMillis() - startTime, 
                JsonUtil.toJson(result));
            return result;
        } catch (Throwable e) {
            log.error("执行异常 {}.{}, 耗时: {}ms, 异常信息: {}", 
                className, methodName, 
                System.currentTimeMillis() - startTime, 
                e.getMessage(), e);
            throw e;
        }
    }
} 