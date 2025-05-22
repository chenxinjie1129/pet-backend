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
import java.util.List;
import java.util.Map;

/**
 * 用户控制器
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户注册", notes = "注册新用户并返回登录信息，包括用户ID、用户名、昵称、头像和JWT令牌")
    @PostMapping("/register")
    public ApiResponse<Map<String, Object>> register(
            @ApiParam(value = "用户信息", required = true, example = "{\"username\":\"testuser\",\"password\":\"password123\",\"mobile\":\"13800138000\",\"nickname\":\"测试用户\",\"gender\":1,\"email\":\"test@example.com\"}")
            @RequestBody User user) {
        // 验证必要参数
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return ApiResponse.error("用户名不能为空");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return ApiResponse.error("密码不能为空");
        }
        if (user.getMobile() == null || user.getMobile().isEmpty()) {
            return ApiResponse.error("手机号不能为空");
        }

        // 注册用户
        boolean result = userService.register(user);

        if (result) {
            try {
                // 获取注册后的用户信息
                User registeredUser = userService.getUserByUsername(user.getUsername());

                // 生成JWT Token
                final UserDetails userDetails = userDetailsService.loadUserByUsername(registeredUser.getUsername());
                final String token = jwtTokenUtil.generateToken(userDetails);

                // 生成登录信息
                Map<String, Object> loginInfo = new HashMap<>();
                loginInfo.put("token", token);
                loginInfo.put("userId", registeredUser.getId());
                loginInfo.put("username", registeredUser.getUsername());
                loginInfo.put("nickname", registeredUser.getNickname());
                loginInfo.put("avatar", registeredUser.getAvatar());

                return ApiResponse.successWithMsg("注册成功", loginInfo);
            } catch (Exception e) {
                log.error("生成JWT Token失败", e);
                return ApiResponse.error("注册成功但登录失败：" + e.getMessage());
            }
        } else {
            return ApiResponse.error("注册失败，用户名或手机号已存在");
        }
    }

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ApiResponse<Map<String, Object>> login(
            @ApiParam("用户名/手机号") @RequestParam String username,
            @ApiParam("密码") @RequestParam String password,
            HttpServletRequest request
    ) {
        String ip = getClientIp(request);
        User user = userService.login(username, password, ip);

        if (user != null) {
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

    @ApiOperation("手机验证码登录")
    @PostMapping("/login/mobile")
    public ApiResponse<Map<String, Object>> loginByMobile(
            @ApiParam("手机号") @RequestParam String mobile,
            @ApiParam("验证码") @RequestParam String code,
            HttpServletRequest request
    ) {
        String ip = getClientIp(request);
        User user = userService.loginByMobile(mobile, code, ip);

        if (user != null) {
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
            return ApiResponse.error("登录失败，手机号或验证码错误");
        }
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/{id}")
    public ApiResponse<User> getUserInfo(@PathVariable Long id) {
        User user = userService.getUserById(id);

        if (user != null) {
            // 不返回敏感信息
            user.setPassword(null);
            user.setSalt(null);
            return ApiResponse.success(user);
        } else {
            return ApiResponse.error("用户不存在");
        }
    }

    @ApiOperation("更新用户信息")
    @PutMapping("/{id}")
    public ApiResponse<User> updateUserInfo(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        boolean result = userService.updateUser(user);

        if (result) {
            User updatedUser = userService.getUserById(id);
            // 不返回敏感信息
            updatedUser.setPassword(null);
            updatedUser.setSalt(null);
            return ApiResponse.successWithMsg("更新成功", updatedUser);
        } else {
            return ApiResponse.error("更新失败");
        }
    }

    @ApiOperation("修改密码")
    @PostMapping("/{id}/password")
    public ApiResponse<Void> changePassword(
            @PathVariable Long id,
            @ApiParam("旧密码") @RequestParam String oldPassword,
            @ApiParam("新密码") @RequestParam String newPassword
    ) {
        boolean result = userService.changePassword(id, oldPassword, newPassword);

        if (result) {
            return ApiResponse.successWithMsg("密码修改成功", null);
        } else {
            return ApiResponse.error("密码修改失败，旧密码错误");
        }
    }

    @ApiOperation("获取用户列表")
    @GetMapping("/list")
    public ApiResponse<List<User>> getUserList() {
        List<User> userList = userService.getUserList();

        // 移除敏感信息
        userList.forEach(user -> {
            user.setPassword(null);
            user.setSalt(null);
        });

        return ApiResponse.success(userList);
    }

    @ApiOperation("更新用户状态")
    @PutMapping("/{id}/status/{status}")
    public ApiResponse<Void> updateUserStatus(
            @PathVariable Long id,
            @PathVariable Integer status
    ) {
        boolean result = userService.updateUserStatus(id, status);

        if (result) {
            return ApiResponse.successWithMsg("状态更新成功", null);
        } else {
            return ApiResponse.error("状态更新失败");
        }
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        boolean result = userService.deleteUser(id);

        if (result) {
            return ApiResponse.successWithMsg("删除成功", null);
        } else {
            return ApiResponse.error("删除失败");
        }
    }

    @ApiOperation("批量删除用户")
    @DeleteMapping("/batch")
    public ApiResponse<Void> batchDeleteUser(@RequestBody List<Long> ids) {
        boolean result = userService.batchDeleteUser(ids);

        if (result) {
            return ApiResponse.successWithMsg("批量删除成功", null);
        } else {
            return ApiResponse.error("批量删除失败");
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