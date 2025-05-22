package com.petshome.api.service;

import com.petshome.api.model.entity.User;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService {
    
    /**
     * 根据ID获取用户
     * @param id 用户ID
     * @return 用户对象
     */
    User getUserById(Long id);
    
    /**
     * 根据用户名获取用户
     * @param username 用户名
     * @return 用户对象
     */
    User getUserByUsername(String username);
    
    /**
     * 根据手机号获取用户
     * @param mobile 手机号
     * @return 用户对象
     */
    User getUserByMobile(String mobile);
    
    /**
     * 用户注册
     * @param user 用户信息
     * @return 注册结果
     */
    boolean register(User user);
    
    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 更新结果
     */
    boolean updateUser(User user);
    
    /**
     * 删除用户
     * @param id 用户ID
     * @return 删除结果
     */
    boolean deleteUser(Long id);
    
    /**
     * 批量删除用户
     * @param ids ID集合
     * @return 删除结果
     */
    boolean batchDeleteUser(List<Long> ids);
    
    /**
     * 获取用户列表
     * @return 用户列表
     */
    List<User> getUserList();
    
    /**
     * 更新用户状态
     * @param id 用户ID
     * @param status 状态值
     * @return 更新结果
     */
    boolean updateUserStatus(Long id, Integer status);
    
    /**
     * 更新用户登录信息
     * @param id 用户ID
     * @param ip 登录IP
     * @return 更新结果
     */
    boolean updateLoginInfo(Long id, String ip);
    
    /**
     * 用户登录
     * @param username 用户名/手机号
     * @param password 密码
     * @param ip 登录IP
     * @return 登录成功的用户信息
     */
    User login(String username, String password, String ip);
    
    /**
     * 手机验证码登录
     * @param mobile 手机号
     * @param code 验证码
     * @param ip 登录IP
     * @return 登录成功的用户信息
     */
    User loginByMobile(String mobile, String code, String ip);
    
    /**
     * 修改密码
     * @param userId 用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 修改结果
     */
    boolean changePassword(Long userId, String oldPassword, String newPassword);
} 