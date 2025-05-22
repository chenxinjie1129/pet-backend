package com.petshome.api.repository;

import com.petshome.api.model.entity.Pet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 宠物数据访问接口
 */
@Mapper
public interface PetMapper {
    
    /**
     * 根据ID查询宠物
     * @param id 宠物ID
     * @return 宠物对象
     */
    Pet selectById(Long id);
    
    /**
     * 根据用户ID查询宠物列表
     * @param userId 用户ID
     * @return 宠物列表
     */
    List<Pet> selectByUserId(Long userId);
    
    /**
     * 插入宠物
     * @param pet 宠物对象
     * @return 影响行数
     */
    int insert(Pet pet);
    
    /**
     * 更新宠物
     * @param pet 宠物对象
     * @return 影响行数
     */
    int update(Pet pet);
    
    /**
     * 删除宠物
     * @param id 宠物ID
     * @param userId 用户ID（用于验证权限）
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id, @Param("userId") Long userId);
    
    /**
     * 批量删除宠物
     * @param ids ID集合
     * @param userId 用户ID（用于验证权限）
     * @return 影响行数
     */
    int deleteByIds(@Param("ids") List<Long> ids, @Param("userId") Long userId);
    
    /**
     * 查询所有宠物
     * @return 宠物列表
     */
    List<Pet> selectAll();
    
    /**
     * 更新宠物状态
     * @param id 宠物ID
     * @param status 状态值
     * @param userId 用户ID（用于验证权限）
     * @return 影响行数
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status, @Param("userId") Long userId);
}
