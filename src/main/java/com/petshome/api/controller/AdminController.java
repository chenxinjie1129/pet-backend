package com.petshome.api.controller;

import com.petshome.api.common.ApiResponse;
import com.petshome.api.model.entity.User;
import com.petshome.api.security.JwtTokenUtil;
import com.petshome.api.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员控制器
 */
@Api(tags = "管理员接口")
@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

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

    @ApiOperation("管理员登录")
    @PostMapping("/login")
    public ApiResponse<Map<String, Object>> login(
            @ApiParam("用户名") @RequestParam String username,
            @ApiParam("密码") @RequestParam String password,
            HttpServletRequest request
    ) {
        String ip = getClientIp(request);
        User user = userService.login(username, password, ip);

        if (user != null) {
            // 检查是否是管理员账号
            if (!"admin".equals(user.getUsername())) {
                return ApiResponse.error("非管理员账号，无法登录管理后台");
            }

            try {
                // 生成JWT Token
                final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
                final String token = jwtTokenUtil.generateToken(userDetails);

                // 生成登录信息
                Map<String, Object> loginInfo = new HashMap<>();
                loginInfo.put("token", token);
                loginInfo.put("userId", user.getId());
                loginInfo.put("username", user.getUsername());
                loginInfo.put("nickname", user.getNickname());
                loginInfo.put("avatar", user.getAvatar());

                return ApiResponse.successWithMsg("登录成功", loginInfo);
            } catch (Exception e) {
                log.error("生成JWT Token失败", e);
                return ApiResponse.error("登录失败：" + e.getMessage());
            }
        } else {
            return ApiResponse.error("登录失败，用户名或密码错误");
        }
    }

    @ApiOperation("获取管理员信息")
    @GetMapping("/info")
    public ApiResponse<User> getAdminInfo(@RequestParam Long userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            // 出于安全考虑，不返回敏感信息
            user.setPassword(null);
            user.setSalt(null);
            return ApiResponse.success(user);
        } else {
            return ApiResponse.error("获取管理员信息失败");
        }
    }

    @ApiOperation("管理员登出")
    @PostMapping("/logout")
    public ApiResponse<Void> logout() {
        return ApiResponse.successWithMsg("登出成功", null);
    }
}
