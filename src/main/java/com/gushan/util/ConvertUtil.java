package com.gushan.util;

import com.gushan.entity.FailLog;
import com.gushan.model.request.FailLogRequest;
import com.gushan.model.response.FailLogResponse;
import org.springframework.beans.BeanUtils;

/**
 * 转换工具类
 *
 * @author gushan
 */
public class ConvertUtil {
    private ConvertUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static FailLog toEntity(FailLogRequest request) {
        if (request == null) {
            return null;
        }
        FailLog entity = new FailLog();
        BeanUtils.copyProperties(request, entity);
        return entity;
    }

    public static FailLogResponse toResponse(FailLog entity) {
        if (entity == null) {
            return null;
        }
        FailLogResponse response = new FailLogResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }
} 