package com.petshome.api.repository;

import com.petshome.api.model.entity.CartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 购物车数据访问接口
 */
@Mapper
public interface CartItemMapper {
    
    /**
     * 根据ID查询购物车项
     * 
     * @param id 购物车项ID
     * @return 购物车项
     */
    CartItem selectById(Long id);
    
    /**
     * 根据用户ID查询购物车项
     * 
     * @param userId 用户ID
     * @return 购物车项列表
     */
    List<CartItem> selectByUserId(Long userId);
    
    /**
     * 根据用户ID和商品ID查询购物车项
     * 
     * @param userId 用户ID
     * @param productId 商品ID
     * @return 购物车项
     */
    CartItem selectByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);
    
    /**
     * 插入购物车项
     * 
     * @param cartItem 购物车项
     * @return 影响行数
     */
    int insert(CartItem cartItem);
    
    /**
     * 更新购物车项
     * 
     * @param cartItem 购物车项
     * @return 影响行数
     */
    int update(CartItem cartItem);
    
    /**
     * 更新购物车项数量
     * 
     * @param id 购物车项ID
     * @param quantity 数量
     * @return 影响行数
     */
    int updateQuantity(@Param("id") Long id, @Param("quantity") Integer quantity);
    
    /**
     * 删除购物车项
     * 
     * @param id 购物车项ID
     * @return 影响行数
     */
    int deleteById(Long id);
    
    /**
     * 根据用户ID删除购物车项
     * 
     * @param userId 用户ID
     * @return 影响行数
     */
    int deleteByUserId(Long userId);
    
    /**
     * 根据用户ID和商品ID删除购物车项
     * 
     * @param userId 用户ID
     * @param productId 商品ID
     * @return 影响行数
     */
    int deleteByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);
    
    /**
     * 根据用户ID和购物车项ID列表删除购物车项
     * 
     * @param userId 用户ID
     * @param ids 购物车项ID列表
     * @return 影响行数
     */
    int deleteByUserIdAndIds(@Param("userId") Long userId, @Param("ids") List<Long> ids);
}
