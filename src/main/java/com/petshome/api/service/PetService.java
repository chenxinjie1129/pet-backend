package com.petshome.api.service;

import com.petshome.api.model.entity.Pet;

import java.util.List;

/**
 * 宠物服务接口
 */
public interface PetService {
    
    /**
     * 根据ID获取宠物
     * @param id 宠物ID
     * @return 宠物对象
     */
    Pet getPetById(Long id);
    
    /**
     * 根据用户ID获取宠物列表
     * @param userId 用户ID
     * @return 宠物列表
     */
    List<Pet> getPetsByUserId(Long userId);
    
    /**
     * 添加宠物
     * @param pet 宠物信息
     * @return 添加结果
     */
    boolean addPet(Pet pet);
    
    /**
     * 更新宠物信息
     * @param pet 宠物信息
     * @return 更新结果
     */
    boolean updatePet(Pet pet);
    
    /**
     * 删除宠物
     * @param id 宠物ID
     * @param userId 用户ID（用于验证权限）
     * @return 删除结果
     */
    boolean deletePet(Long id, Long userId);
    
    /**
     * 批量删除宠物
     * @param ids ID集合
     * @param userId 用户ID（用于验证权限）
     * @return 删除结果
     */
    boolean batchDeletePet(List<Long> ids, Long userId);
    
    /**
     * 获取宠物列表
     * @return 宠物列表
     */
    List<Pet> getPetList();
    
    /**
     * 更新宠物状态
     * @param id 宠物ID
     * @param status 状态值
     * @param userId 用户ID（用于验证权限）
     * @return 更新结果
     */
    boolean updatePetStatus(Long id, Integer status, Long userId);
}
