package com.petshome.api.security;

import com.petshome.api.model.entity.User;
import com.petshome.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义UserDetailsService实现，用于Spring Security认证
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 先尝试用用户名查找
        User user = userService.getUserByUsername(username);

        // 如果找不到，尝试用手机号查找
        if (user == null) {
            user = userService.getUserByMobile(username);
        }

        if (user == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }

        // 如果用户被禁用
        if (user.getStatus() != 1) {
            throw new UsernameNotFoundException("用户已被禁用: " + username);
        }

        // 创建授权列表，这里简单处理，后续可以扩展为角色和权限
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        // 如果是管理员账号，添加管理员角色
        if ("admin".equals(user.getUsername())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        // 返回UserDetails对象
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}
