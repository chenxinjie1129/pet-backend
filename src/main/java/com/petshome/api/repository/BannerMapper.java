package com.petshome.api.repository;

import com.petshome.api.model.entity.Banner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 轮播图数据访问接口
 */
@Mapper
public interface BannerMapper {
    
    /**
     * 根据ID查询轮播图
     * @param id 轮播图ID
     * @return 轮播图对象
     */
    Banner selectById(Long id);
    
    /**
     * 查询所有轮播图
     * @return 轮播图列表
     */
    List<Banner> selectAll();
    
    /**
     * 查询启用的轮播图
     * @return 轮播图列表
     */
    List<Banner> selectActive();
    
    /**
     * 插入轮播图
     * @param banner 轮播图对象
     * @return 影响行数
     */
    int insert(Banner banner);
    
    /**
     * 更新轮播图
     * @param banner 轮播图对象
     * @return 影响行数
     */
    int update(Banner banner);
    
    /**
     * 删除轮播图
     * @param id 轮播图ID
     * @return 影响行数
     */
    int deleteById(Long id);
    
    /**
     * 批量删除轮播图
     * @param ids ID集合
     * @return 影响行数
     */
    int deleteByIds(@Param("ids") List<Long> ids);
    
    /**
     * 更新轮播图状态
     * @param id 轮播图ID
     * @param status 状态值
     * @return 影响行数
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
}
