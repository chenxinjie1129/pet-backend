package com.petshome.api.security;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT认证过滤器，用于拦截请求并验证JWT令牌
 */
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        log.info("处理请求: {}", request.getRequestURI());

        final String requestTokenHeader = request.getHeader("Authorization");
        log.info("Authorization头: {}", requestTokenHeader);

        String username = null;
        String jwtToken = null;

        // 处理JWT令牌
        if (requestTokenHeader != null) {
            log.info("收到的请求头: {}", requestTokenHeader);

            // 尝试不同的格式
            if (requestTokenHeader.startsWith("Bearer ")) {
                // 标准格式: "Bearer token"
                jwtToken = requestTokenHeader.substring(7);
                log.info("从Bearer前缀中提取的JWT令牌: {}", jwtToken);
            } else if (requestTokenHeader.startsWith("bearer ")) {
                // 小写格式: "bearer token"
                jwtToken = requestTokenHeader.substring(7);
                log.info("从bearer前缀中提取的JWT令牌: {}", jwtToken);
            } else {
                // 直接使用令牌
                jwtToken = requestTokenHeader;
                log.info("直接使用的JWT令牌: {}", jwtToken);
            }

            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                log.info("从令牌中提取的用户名: {}", username);
            } catch (IllegalArgumentException e) {
                log.error("获取JWT Token失败", e);
            } catch (ExpiredJwtException e) {
                log.error("JWT Token已过期", e);
            } catch (Exception e) {
                log.error("解析JWT Token时发生未知错误", e);
            }
        } else {
            log.warn("JWT Token为空");
        }

        // 验证token
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.info("开始验证用户: {}", username);
            try {
                UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
                log.info("加载到用户详情: {}, 权限: {}", userDetails, userDetails.getAuthorities());

                // 如果token有效，则配置Spring Security使用该token
                if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                    log.info("令牌验证成功");
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // 设置当前用户认证信息
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("已设置认证信息");
                } else {
                    log.warn("令牌验证失败");
                }
            } catch (Exception e) {
                log.error("验证用户时发生错误", e);
            }
        } else {
            log.info("跳过认证: username={}, 已有认证={}", username,
                    SecurityContextHolder.getContext().getAuthentication() != null);
        }

        chain.doFilter(request, response);
    }
}
