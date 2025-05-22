package com.petshome.api.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.petshome.api.model.entity.User;
import com.petshome.api.repository.UserMapper;
import com.petshome.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 用户服务实现类
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }
    
    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }
    
    @Override
    public User getUserByMobile(String mobile) {
        return userMapper.selectByMobile(mobile);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean register(User user) {
        // 检查用户名是否已存在
        if (getUserByUsername(user.getUsername()) != null) {
            log.info("用户名已存在: {}", user.getUsername());
            return false;
        }
        
        // 检查手机号是否已存在
        if (getUserByMobile(user.getMobile()) != null) {
            log.info("手机号已存在: {}", user.getMobile());
            return false;
        }
        
        // 生成盐值
        String salt = IdUtil.simpleUUID();
        user.setSalt(salt);
        
        // 密码加密
        String encryptedPassword = DigestUtil.md5Hex(user.getPassword() + salt);
        user.setPassword(encryptedPassword);
        
        // 设置默认值
        if (user.getStatus() == null) {
            user.setStatus(1); // 默认启用
        }
        if (user.getGender() == null) {
            user.setGender(0); // 默认未知
        }
        if (user.getNickname() == null || user.getNickname().isEmpty()) {
            user.setNickname(user.getUsername()); // 默认使用用户名作为昵称
        }
        
        // 设置时间
        Date now = new Date();
        user.setCreateTime(now);
        user.setUpdateTime(now);
        
        return userMapper.insert(user) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(User user) {
        // 获取原用户信息
        User oldUser = getUserById(user.getId());
        if (oldUser == null) {
            log.info("用户不存在: {}", user.getId());
            return false;
        }
        
        // 如果修改了用户名，检查新用户名是否存在
        if (user.getUsername() != null && !user.getUsername().equals(oldUser.getUsername())) {
            if (getUserByUsername(user.getUsername()) != null) {
                log.info("新用户名已存在: {}", user.getUsername());
                return false;
            }
        }
        
        // 如果修改了手机号，检查新手机号是否存在
        if (user.getMobile() != null && !user.getMobile().equals(oldUser.getMobile())) {
            if (getUserByMobile(user.getMobile()) != null) {
                log.info("新手机号已存在: {}", user.getMobile());
                return false;
            }
        }
        
        // 设置更新时间
        user.setUpdateTime(new Date());
        
        return userMapper.update(user) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUser(Long id) {
        return userMapper.deleteById(id) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDeleteUser(List<Long> ids) {
        return userMapper.deleteByIds(ids) > 0;
    }
    
    @Override
    public List<User> getUserList() {
        return userMapper.selectAll();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserStatus(Long id, Integer status) {
        return userMapper.updateStatus(id, status) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateLoginInfo(Long id, String ip) {
        return userMapper.updateLoginInfo(id, ip) > 0;
    }
    
    @Override
    public User login(String username, String password, String ip) {
        // 先尝试用户名登录
        User user = getUserByUsername(username);
        
        // 如果找不到用户，尝试用手机号登录
        if (user == null) {
            user = getUserByMobile(username);
        }
        
        // 如果用户不存在
        if (user == null) {
            log.info("用户不存在: {}", username);
            return null;
        }
        
        // 如果用户被禁用
        if (user.getStatus() != 1) {
            log.info("用户已被禁用: {}", username);
            return null;
        }
        
        // 验证密码
        String encryptedPassword = DigestUtil.md5Hex(password + user.getSalt());
        if (!encryptedPassword.equals(user.getPassword())) {
            log.info("密码错误: {}", username);
            return null;
        }
        
        // 更新登录信息
        updateLoginInfo(user.getId(), ip);
        
        return user;
    }
    
    @Override
    public User loginByMobile(String mobile, String code, String ip) {
        // 根据手机号查找用户
        User user = getUserByMobile(mobile);
        
        // 如果用户不存在
        if (user == null) {
            log.info("用户不存在: {}", mobile);
            return null;
        }
        
        // 如果用户被禁用
        if (user.getStatus() != 1) {
            log.info("用户已被禁用: {}", mobile);
            return null;
        }
        
        // 验证验证码逻辑，这里应该调用验证码服务进行验证
        // TODO: 实现验证码验证逻辑
        
        // 更新登录信息
        updateLoginInfo(user.getId(), ip);
        
        return user;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        // 获取用户信息
        User user = getUserById(userId);
        if (user == null) {
            log.info("用户不存在: {}", userId);
            return false;
        }
        
        // 验证旧密码
        String encryptedOldPassword = DigestUtil.md5Hex(oldPassword + user.getSalt());
        if (!encryptedOldPassword.equals(user.getPassword())) {
            log.info("旧密码错误: {}", userId);
            return false;
        }
        
        // 加密新密码
        String encryptedNewPassword = DigestUtil.md5Hex(newPassword + user.getSalt());
        
        // 更新密码
        User updateUser = new User();
        updateUser.setId(userId);
        updateUser.setPassword(encryptedNewPassword);
        updateUser.setUpdateTime(new Date());
        
        return userMapper.update(updateUser) > 0;
    }
} 