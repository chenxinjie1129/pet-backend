package com.petshome.api.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 调试日志记录器
 */
@Component
@Slf4j
public class DebugLogger {

    /**
     * 记录访问日志
     * @param request HTTP请求
     * @param message 日志消息
     * @return 始终返回true，允许访问
     */
    public boolean logAccess(HttpServletRequest request, String message) {
        log.info("{}: {} {}", message, request.getMethod(), request.getRequestURI());
        log.info("请求头: {}", request.getHeader("Authorization"));
        return true;
    }
}
