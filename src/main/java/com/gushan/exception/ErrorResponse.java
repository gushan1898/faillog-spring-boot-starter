package com.gushan.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 错误响应
 *
 * @author gushan
 */
@Data
@AllArgsConstructor
public class ErrorResponse {
    private int code;
    private String message;
} 