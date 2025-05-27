package com.petshome.api.service;

import com.petshome.api.model.entity.ProductCategory;

import java.util.List;

/**
 * 商品分类服务接口
 */
public interface ProductCategoryService {

    /**
     * 根据ID查询商品分类
     *
     * @param id 分类ID
     * @return 商品分类
     */
    ProductCategory getById(Long id);

    /**
     * 查询所有商品分类
     *
     * @return 商品分类列表
     */
    List<ProductCategory> getAll();



    /**
     * 添加商品分类
     *
     * @param category 商品分类
     * @return 添加后的商品分类
     */
    ProductCategory add(ProductCategory category);

    /**
     * 更新商品分类
     *
     * @param category 商品分类
     * @return 更新后的商品分类
     */
    ProductCategory update(ProductCategory category);

    /**
     * 删除商品分类
     *
     * @param id 分类ID
     * @return 是否成功
     */
    boolean delete(Long id);

    /**
     * 更新商品分类状态
     *
     * @param id 分类ID
     * @param status 状态
     * @return 是否成功
     */
    boolean updateStatus(Long id, Integer status);
}
