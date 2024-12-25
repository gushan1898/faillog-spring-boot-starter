package com.gushan.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gushan.exception.FailLogException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * JSON工具类
 *
 * @author gushan
 */
@Slf4j
public class JsonUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .registerModule(new JavaTimeModule());

    private JsonUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String toJson(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("转换JSON失败", e);
            throw new FailLogException("转换JSON失败", e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            log.error("解析JSON失败", e);
            throw new FailLogException("解析JSON失败", e);
        }
    }
} 