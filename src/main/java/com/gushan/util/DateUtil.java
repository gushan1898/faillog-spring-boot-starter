package com.gushan.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期工具类
 *
 * @author gushan
 */
public class DateUtil {
    private static final DateTimeFormatter DEFAULT_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private DateUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String format(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DEFAULT_FORMATTER) : "";
    }

    public static LocalDateTime parse(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DEFAULT_FORMATTER);
    }
} 