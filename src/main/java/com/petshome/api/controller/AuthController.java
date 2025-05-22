package com.petshome.api.controller;

import com.petshome.api.common.ApiResponse;
import com.petshome.api.model.entity.User;
import com.petshome.api.security.JwtTokenUtil;
import com.petshome.api.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 */
@Api(tags = "认证管理")
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    /**
     * 用户登录接口已移至UserController，此接口已废弃
     * @deprecated 请使用 {@link com.petshome.api.controller.UserController#login(String, String, HttpServletRequest)}
     */
    @Deprecated
    @ApiOperation(value = "用户登录（已废弃）", notes = "此接口已废弃，请使用/user/login接口")
    @PostMapping("/login")
    public ApiResponse<Map<String, Object>> login(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletRequest request
    ) {
        return ApiResponse.error("此接口已废弃，请使用/user/login接口");
    }

    /**
     * 手机验证码登录接口已移至UserController，此接口已废弃
     * @deprecated 请使用 {@link com.petshome.api.controller.UserController#loginByMobile(String, String, HttpServletRequest)}
     */
    @Deprecated
    @ApiOperation(value = "手机验证码登录（已废弃）", notes = "此接口已废弃，请使用/user/login/mobile接口")
    @PostMapping("/login/mobile")
    public ApiResponse<Map<String, Object>> loginByMobile(
            @RequestParam String mobile,
            @RequestParam String code,
            HttpServletRequest request
    ) {
        return ApiResponse.error("此接口已废弃，请使用/user/login/mobile接口");
    }

    /**
     * 认证用户
     */
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("用户已被禁用", e);
        } catch (BadCredentialsException e) {
            throw new Exception("用户名或密码错误", e);
        }
    }

    /**
     * 获取客户端IP地址
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
