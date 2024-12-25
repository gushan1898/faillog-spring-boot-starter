package com.gushan.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Spring Context 工具类，用于获取 Spring 容器中的 Bean
 *
 * @author gushan
 */
@Slf4j
@Component
@Lazy(false)
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext = null;

    /**
     * 获取 ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    /**
     * 根据名称获取 Bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 根据类型获取 Bean
     */
    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    /**
     * 根据名称和类型获取 Bean
     */
    public static <T> T getBean(String name, Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(name, requiredType);
    }

    /**
     * 清除 SpringContextHolder 中的 ApplicationContext
     */
    public static void clearHolder() {
        if (log.isDebugEnabled()) {
            log.debug("清除 SpringContextHolder 中的 ApplicationContext: {}", applicationContext);
        }
        applicationContext = null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringContextHolder.applicationContext != null) {
            log.warn("SpringContextHolder 中的 ApplicationContext 被覆盖, 原有 ApplicationContext 为: {}", 
                SpringContextHolder.applicationContext);
        }
        SpringContextHolder.applicationContext = applicationContext;
    }

    @Override
    public void destroy() {
        clearHolder();
    }

    /**
     * 检查 ApplicationContext 是否注入
     */
    private static void assertContextInjected() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicaitonContext 未注入，" +
                "请在 applicationContext.xml 中定义 SpringContextHolder");
        }
    }
} 