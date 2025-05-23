package com.petshome.api.service;

import com.petshome.api.common.Pagination;
import com.petshome.api.model.entity.Product;

import java.util.List;
import java.util.Map;

/**
 * 商品服务接口
 */
public interface ProductService {
    
    /**
     * 根据ID查询商品
     * 
     * @param id 商品ID
     * @return 商品
     */
    Product getById(Long id);
    
    /**
     * 查询所有商品
     * 
     * @return 商品列表
     */
    List<Product> getAll();
    
    /**
     * 根据分类ID查询商品
     * 
     * @param categoryId 分类ID
     * @return 商品列表
     */
    List<Product> getByCategoryId(Long categoryId);
    
    /**
     * 根据关键词搜索商品
     * 
     * @param keyword 关键词
     * @return 商品列表
     */
    List<Product> getByKeyword(String keyword);
    
    /**
     * 分页查询商品
     * 
     * @param categoryId 分类ID
     * @param keyword 关键词
     * @param page 页码
     * @param size 每页数量
     * @return 商品列表和分页信息
     */
    Map<String, Object> getByPage(Long categoryId, String keyword, Integer page, Integer size);
    
    /**
     * 查询热门商品
     * 
     * @param limit 限制数量
     * @return 商品列表
     */
    List<Product> getHotProducts(Integer limit);
    
    /**
     * 添加商品
     * 
     * @param product 商品
     * @return 添加后的商品
     */
    Product add(Product product);
    
    /**
     * 更新商品
     * 
     * @param product 商品
     * @return 更新后的商品
     */
    Product update(Product product);
    
    /**
     * 删除商品
     * 
     * @param id 商品ID
     * @return 是否成功
     */
    boolean delete(Long id);
    
    /**
     * 更新商品状态
     * 
     * @param id 商品ID
     * @param status 状态
     * @return 是否成功
     */
    boolean updateStatus(Long id, Integer status);
    
    /**
     * 更新商品库存
     * 
     * @param id 商品ID
     * @param stock 库存
     * @return 是否成功
     */
    boolean updateStock(Long id, Integer stock);
    
    /**
     * 增加商品销量
     * 
     * @param id 商品ID
     * @param sales 销量增量
     * @return 是否成功
     */
    boolean increaseSales(Long id, Integer sales);
}
