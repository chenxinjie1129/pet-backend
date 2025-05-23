package com.petshome.api.repository;

import com.petshome.api.model.entity.ProductCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品分类数据访问接口
 */
@Mapper
public interface ProductCategoryMapper {
    
    /**
     * 根据ID查询商品分类
     * 
     * @param id 分类ID
     * @return 商品分类
     */
    ProductCategory selectById(Long id);
    
    /**
     * 查询所有商品分类
     * 
     * @return 商品分类列表
     */
    List<ProductCategory> selectAll();
    
    /**
     * 根据父分类ID查询子分类
     * 
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    List<ProductCategory> selectByParentId(Long parentId);
    
    /**
     * 根据级别查询分类
     * 
     * @param level 级别
     * @return 分类列表
     */
    List<ProductCategory> selectByLevel(Integer level);
    
    /**
     * 插入商品分类
     * 
     * @param category 商品分类
     * @return 影响行数
     */
    int insert(ProductCategory category);
    
    /**
     * 更新商品分类
     * 
     * @param category 商品分类
     * @return 影响行数
     */
    int update(ProductCategory category);
    
    /**
     * 删除商品分类
     * 
     * @param id 分类ID
     * @return 影响行数
     */
    int deleteById(Long id);
    
    /**
     * 更新商品分类状态
     * 
     * @param id 分类ID
     * @param status 状态
     * @return 影响行数
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
}
