package com.petshome.api.repository;

import com.petshome.api.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户数据访问接口
 */
@Mapper
public interface UserMapper {
    
    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户对象
     */
    User selectById(Long id);
    
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户对象
     */
    User selectByUsername(String username);
    
    /**
     * 根据手机号查询用户
     * @param mobile 手机号
     * @return 用户对象
     */
    User selectByMobile(String mobile);
    
    /**
     * 插入用户
     * @param user 用户对象
     * @return 影响行数
     */
    int insert(User user);
    
    /**
     * 更新用户
     * @param user 用户对象
     * @return 影响行数
     */
    int update(User user);
    
    /**
     * 删除用户
     * @param id 用户ID
     * @return 影响行数
     */
    int deleteById(Long id);
    
    /**
     * 批量删除用户
     * @param ids ID集合
     * @return 影响行数
     */
    int deleteByIds(@Param("ids") List<Long> ids);
    
    /**
     * 查询所有用户
     * @return 用户列表
     */
    List<User> selectAll();
    
    /**
     * 更新用户状态
     * @param id 用户ID
     * @param status 状态值
     * @return 影响行数
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    
    /**
     * 更新用户登录信息
     * @param id 用户ID
     * @param ip 登录IP
     * @return 影响行数
     */
    int updateLoginInfo(@Param("id") Long id, @Param("ip") String ip);
} 