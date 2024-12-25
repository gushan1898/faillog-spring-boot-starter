package com.gushan.exception;

import com.gushan.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author gushan
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FailLogException.class)
    public Result<Void> handleFailLogException(FailLogException e) {
        log.error("业务异常", e);
        return Result.error(400, e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public Result<Void> handleBindException(BindException e) {
        log.error("参数校验异常", e);
        return Result.error(400, e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("系统异常", e);
        return Result.error("系统异常");
    }
} 