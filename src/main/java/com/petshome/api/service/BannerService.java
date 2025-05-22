package com.petshome.api.service;

import com.petshome.api.model.entity.Banner;

import java.util.List;

/**
 * 轮播图服务接口
 */
public interface BannerService {
    
    /**
     * 根据ID获取轮播图
     * @param id 轮播图ID
     * @return 轮播图对象
     */
    Banner getBannerById(Long id);
    
    /**
     * 获取轮播图列表
     * @return 轮播图列表
     */
    List<Banner> getBannerList();
    
    /**
     * 获取启用的轮播图列表
     * @return 轮播图列表
     */
    List<Banner> getActiveBannerList();
    
    /**
     * 添加轮播图
     * @param banner 轮播图信息
     * @return 添加结果
     */
    boolean addBanner(Banner banner);
    
    /**
     * 更新轮播图
     * @param banner 轮播图信息
     * @return 更新结果
     */
    boolean updateBanner(Banner banner);
    
    /**
     * 删除轮播图
     * @param id 轮播图ID
     * @return 删除结果
     */
    boolean deleteBanner(Long id);
    
    /**
     * 批量删除轮播图
     * @param ids ID集合
     * @return 删除结果
     */
    boolean batchDeleteBanner(List<Long> ids);
    
    /**
     * 更新轮播图状态
     * @param id 轮播图ID
     * @param status 状态值
     * @return 更新结果
     */
    boolean updateBannerStatus(Long id, Integer status);
}
