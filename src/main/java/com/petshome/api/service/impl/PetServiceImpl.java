package com.petshome.api.service.impl;

import com.petshome.api.model.entity.Pet;
import com.petshome.api.repository.PetMapper;
import com.petshome.api.service.PetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 宠物服务实现类
 */
@Service
@Slf4j
public class PetServiceImpl implements PetService {
    
    @Autowired
    private PetMapper petMapper;
    
    @Override
    public Pet getPetById(Long id) {
        return petMapper.selectById(id);
    }
    
    @Override
    public List<Pet> getPetsByUserId(Long userId) {
        return petMapper.selectByUserId(userId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addPet(Pet pet) {
        // 设置默认值
        if (pet.getStatus() == null) {
            pet.setStatus(0); // 默认正常
        }
        if (pet.getGender() == null) {
            pet.setGender(0); // 默认未知
        }
        if (pet.getSterilized() == null) {
            pet.setSterilized(0); // 默认未绝育
        }
        
        // 设置时间
        Date now = new Date();
        pet.setCreateTime(now);
        pet.setUpdateTime(now);
        
        return petMapper.insert(pet) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePet(Pet pet) {
        // 获取原宠物信息
        Pet oldPet = getPetById(pet.getId());
        if (oldPet == null) {
            log.info("宠物不存在: {}", pet.getId());
            return false;
        }
        
        // 验证权限
        if (!oldPet.getUserId().equals(pet.getUserId())) {
            log.info("无权限更新宠物: {}, 用户ID: {}", pet.getId(), pet.getUserId());
            return false;
        }
        
        // 设置更新时间
        pet.setUpdateTime(new Date());
        
        return petMapper.update(pet) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deletePet(Long id, Long userId) {
        return petMapper.deleteById(id, userId) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDeletePet(List<Long> ids, Long userId) {
        return petMapper.deleteByIds(ids, userId) > 0;
    }
    
    @Override
    public List<Pet> getPetList() {
        return petMapper.selectAll();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePetStatus(Long id, Integer status, Long userId) {
        return petMapper.updateStatus(id, status, userId) > 0;
    }
}
