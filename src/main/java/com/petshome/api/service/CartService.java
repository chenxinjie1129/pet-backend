package com.petshome.api.service;

import com.petshome.api.model.entity.CartItem;

import java.util.List;
import java.util.Map;

/**
 * 购物车服务接口
 */
public interface CartService {
    
    /**
     * 根据ID查询购物车项
     * 
     * @param id 购物车项ID
     * @return 购物车项
     */
    CartItem getById(Long id);
    
    /**
     * 根据用户ID查询购物车项
     * 
     * @param userId 用户ID
     * @return 购物车项列表
     */
    List<CartItem> getByUserId(Long userId);
    
    /**
     * 获取购物车信息（包含总金额和总数量）
     * 
     * @param userId 用户ID
     * @return 购物车信息
     */
    Map<String, Object> getCartInfo(Long userId);
    
    /**
     * 添加商品到购物车
     * 
     * @param userId 用户ID
     * @param productId 商品ID
     * @param quantity 数量
     * @return 添加后的购物车项
     */
    CartItem addToCart(Long userId, Long productId, Integer quantity);
    
    /**
     * 更新购物车项数量
     * 
     * @param id 购物车项ID
     * @param userId 用户ID
     * @param quantity 数量
     * @return 更新后的购物车项
     */
    CartItem updateQuantity(Long id, Long userId, Integer quantity);
    
    /**
     * 删除购物车项
     * 
     * @param id 购物车项ID
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean delete(Long id, Long userId);
    
    /**
     * 清空购物车
     * 
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean clear(Long userId);
    
    /**
     * 批量删除购物车项
     * 
     * @param ids 购物车项ID列表
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean batchDelete(List<Long> ids, Long userId);
}
