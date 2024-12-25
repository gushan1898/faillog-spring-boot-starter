package com.gushan.controller;

import com.gushan.model.Result;
import com.gushan.model.request.FailLogRequest;
import com.gushan.model.response.FailLogResponse;
import com.gushan.service.FailLogService;
import com.gushan.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 失败日志控制器
 *
 * @author gushan
 */
@RestController
@RequestMapping("/api/fail-logs")
@RequiredArgsConstructor
public class FailLogController {
    private final FailLogService failLogService;

    /**
     * 保存失败日志
     */
    @PostMapping
    public Result<FailLogResponse> save(@Validated @RequestBody FailLogRequest request) {
        failLogService.save(ConvertUtil.toEntity(request));
        return Result.success();
    }

    /**
     * 获取失败日志
     */
    @GetMapping("/{id}")
    public Result<FailLogResponse> getById(@PathVariable Long id) {
        return Result.success(ConvertUtil.toResponse(failLogService.getById(id)));
    }

    /**
     * 获取所有失败日志
     */
    @GetMapping
    public Result<List<FailLogResponse>> list() {
        return Result.success(
            failLogService.list().stream()
                .map(ConvertUtil::toResponse)
                .collect(Collectors.toList())
        );
    }

    /**
     * 删除失败日志
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        failLogService.delete(id);
        return Result.success();
    }

    /**
     * 手动触发重试
     */
    @PostMapping("/retry")
    public Result<Void> retry() {
        failLogService.retryFailedLogs();
        return Result.success();
    }
} 