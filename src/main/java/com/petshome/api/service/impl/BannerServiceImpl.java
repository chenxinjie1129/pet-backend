package com.petshome.api.service.impl;

import com.petshome.api.model.entity.Banner;
import com.petshome.api.repository.BannerMapper;
import com.petshome.api.service.BannerService;
import com.petshome.api.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 轮播图服务实现类
 */
@Service
@Slf4j
public class BannerServiceImpl implements BannerService {
    
    @Autowired
    private BannerMapper bannerMapper;
    
    @Autowired
    private FileService fileService;
    
    @Override
    public Banner getBannerById(Long id) {
        return bannerMapper.selectById(id);
    }
    
    @Override
    public List<Banner> getBannerList() {
        return bannerMapper.selectAll();
    }
    
    @Override
    public List<Banner> getActiveBannerList() {
        return bannerMapper.selectActive();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addBanner(Banner banner) {
        // 设置默认值
        if (banner.getStatus() == null) {
            banner.setStatus(1); // 默认启用
        }
        if (banner.getSort() == null) {
            banner.setSort(0); // 默认排序值
        }
        
        // 设置时间
        Date now = new Date();
        banner.setCreateTime(now);
        banner.setUpdateTime(now);
        
        return bannerMapper.insert(banner) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBanner(Banner banner) {
        // 获取原轮播图信息
        Banner oldBanner = getBannerById(banner.getId());
        if (oldBanner == null) {
            log.info("轮播图不存在: {}", banner.getId());
            return false;
        }
        
        // 设置更新时间
        banner.setUpdateTime(new Date());
        
        return bannerMapper.update(banner) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBanner(Long id) {
        // 获取轮播图信息
        Banner banner = getBannerById(id);
        if (banner == null) {
            return false;
        }
        
        // 删除轮播图
        boolean result = bannerMapper.deleteById(id) > 0;
        
        // 如果删除成功，同时删除图片
        if (result && banner.getImageUrl() != null && !banner.getImageUrl().isEmpty()) {
            fileService.deleteFile(banner.getImageUrl());
        }
        
        return result;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDeleteBanner(List<Long> ids) {
        // 获取轮播图列表
        for (Long id : ids) {
            Banner banner = getBannerById(id);
            if (banner != null && banner.getImageUrl() != null && !banner.getImageUrl().isEmpty()) {
                fileService.deleteFile(banner.getImageUrl());
            }
        }
        
        return bannerMapper.deleteByIds(ids) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBannerStatus(Long id, Integer status) {
        return bannerMapper.updateStatus(id, status) > 0;
    }
}
