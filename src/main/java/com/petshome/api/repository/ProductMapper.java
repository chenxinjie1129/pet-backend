package com.petshome.api.repository;

import com.petshome.api.model.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品数据访问接口
 */
@Mapper
public interface ProductMapper {
    
    /**
     * 根据ID查询商品
     * 
     * @param id 商品ID
     * @return 商品
     */
    Product selectById(Long id);
    
    /**
     * 查询所有商品
     * 
     * @return 商品列表
     */
    List<Product> selectAll();
    
    /**
     * 根据分类ID查询商品
     * 
     * @param categoryId 分类ID
     * @return 商品列表
     */
    List<Product> selectByCategoryId(Long categoryId);
    
    /**
     * 根据关键词搜索商品
     * 
     * @param keyword 关键词
     * @return 商品列表
     */
    List<Product> selectByKeyword(String keyword);
    
    /**
     * 分页查询商品
     * 
     * @param categoryId 分类ID
     * @param keyword 关键词
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 商品列表
     */
    List<Product> selectByPage(@Param("categoryId") Long categoryId, 
                              @Param("keyword") String keyword, 
                              @Param("offset") Integer offset, 
                              @Param("limit") Integer limit);
    
    /**
     * 查询商品总数
     * 
     * @param categoryId 分类ID
     * @param keyword 关键词
     * @return 商品总数
     */
    int countByCondition(@Param("categoryId") Long categoryId, @Param("keyword") String keyword);
    
    /**
     * 查询热门商品
     * 
     * @param limit 限制数量
     * @return 商品列表
     */
    List<Product> selectHotProducts(Integer limit);
    
    /**
     * 插入商品
     * 
     * @param product 商品
     * @return 影响行数
     */
    int insert(Product product);
    
    /**
     * 更新商品
     * 
     * @param product 商品
     * @return 影响行数
     */
    int update(Product product);
    
    /**
     * 删除商品
     * 
     * @param id 商品ID
     * @return 影响行数
     */
    int deleteById(Long id);
    
    /**
     * 更新商品状态
     * 
     * @param id 商品ID
     * @param status 状态
     * @return 影响行数
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    
    /**
     * 更新商品库存
     * 
     * @param id 商品ID
     * @param stock 库存
     * @return 影响行数
     */
    int updateStock(@Param("id") Long id, @Param("stock") Integer stock);
    
    /**
     * 增加商品销量
     * 
     * @param id 商品ID
     * @param sales 销量增量
     * @return 影响行数
     */
    int increaseSales(@Param("id") Long id, @Param("sales") Integer sales);
}
