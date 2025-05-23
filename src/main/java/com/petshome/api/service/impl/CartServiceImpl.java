package com.petshome.api.service.impl;

import com.petshome.api.exception.BusinessException;
import com.petshome.api.model.entity.CartItem;
import com.petshome.api.model.entity.Product;
import com.petshome.api.repository.CartItemMapper;
import com.petshome.api.repository.ProductMapper;
import com.petshome.api.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车服务实现类
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartItemMapper cartItemMapper;
    
    @Autowired
    private ProductMapper productMapper;

    @Override
    public CartItem getById(Long id) {
        return cartItemMapper.selectById(id);
    }

    @Override
    public List<CartItem> getByUserId(Long userId) {
        return cartItemMapper.selectByUserId(userId);
    }

    @Override
    public Map<String, Object> getCartInfo(Long userId) {
        // 查询购物车项
        List<CartItem> cartItems = cartItemMapper.selectByUserId(userId);
        
        // 计算总金额和总数量
        BigDecimal totalAmount = BigDecimal.ZERO;
        int totalQuantity = 0;
        
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct() != null) {
                BigDecimal itemPrice = cartItem.getProduct().getPrice().multiply(new BigDecimal(cartItem.getQuantity()));
                totalAmount = totalAmount.add(itemPrice);
                totalQuantity += cartItem.getQuantity();
            }
        }
        
        // 构建返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("cartItems", cartItems);
        result.put("totalAmount", totalAmount);
        result.put("totalQuantity", totalQuantity);
        
        return result;
    }

    @Override
    @Transactional
    public CartItem addToCart(Long userId, Long productId, Integer quantity) {
        // 参数校验
        if (quantity == null || quantity < 1) {
            throw new BusinessException("商品数量必须大于0");
        }
        
        // 检查商品是否存在
        Product product = productMapper.selectById(productId);
        if (product == null) {
            throw new BusinessException("商品不存在");
        }
        
        // 检查商品是否上架
        if (product.getStatus() != 1) {
            throw new BusinessException("商品已下架");
        }
        
        // 检查库存是否足够
        if (product.getStock() < quantity) {
            throw new BusinessException("商品库存不足");
        }
        
        // 检查购物车中是否已存在该商品
        CartItem existingCartItem = cartItemMapper.selectByUserIdAndProductId(userId, productId);
        
        if (existingCartItem != null) {
            // 更新数量
            int newQuantity = existingCartItem.getQuantity() + quantity;
            
            // 再次检查库存是否足够
            if (product.getStock() < newQuantity) {
                throw new BusinessException("商品库存不足");
            }
            
            existingCartItem.setQuantity(newQuantity);
            cartItemMapper.update(existingCartItem);
            
            return existingCartItem;
        } else {
            // 创建新的购物车项
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(productId);
            cartItem.setQuantity(quantity);
            
            cartItemMapper.insert(cartItem);
            
            // 设置商品信息
            cartItem.setProduct(product);
            
            return cartItem;
        }
    }

    @Override
    @Transactional
    public CartItem updateQuantity(Long id, Long userId, Integer quantity) {
        // 参数校验
        if (quantity == null || quantity < 1) {
            throw new BusinessException("商品数量必须大于0");
        }
        
        // 检查购物车项是否存在
        CartItem cartItem = cartItemMapper.selectById(id);
        if (cartItem == null) {
            throw new BusinessException("购物车项不存在");
        }
        
        // 检查购物车项是否属于当前用户
        if (!cartItem.getUserId().equals(userId)) {
            throw new BusinessException("无权操作该购物车项");
        }
        
        // 检查商品是否存在
        Product product = productMapper.selectById(cartItem.getProductId());
        if (product == null) {
            throw new BusinessException("商品不存在");
        }
        
        // 检查商品是否上架
        if (product.getStatus() != 1) {
            throw new BusinessException("商品已下架");
        }
        
        // 检查库存是否足够
        if (product.getStock() < quantity) {
            throw new BusinessException("商品库存不足");
        }
        
        // 更新数量
        cartItem.setQuantity(quantity);
        cartItemMapper.update(cartItem);
        
        // 设置商品信息
        cartItem.setProduct(product);
        
        return cartItem;
    }

    @Override
    @Transactional
    public boolean delete(Long id, Long userId) {
        // 检查购物车项是否存在
        CartItem cartItem = cartItemMapper.selectById(id);
        if (cartItem == null) {
            throw new BusinessException("购物车项不存在");
        }
        
        // 检查购物车项是否属于当前用户
        if (!cartItem.getUserId().equals(userId)) {
            throw new BusinessException("无权操作该购物车项");
        }
        
        // 删除购物车项
        return cartItemMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional
    public boolean clear(Long userId) {
        return cartItemMapper.deleteByUserId(userId) > 0;
    }

    @Override
    @Transactional
    public boolean batchDelete(List<Long> ids, Long userId) {
        if (ids == null || ids.isEmpty()) {
            return false;
        }
        
        return cartItemMapper.deleteByUserIdAndIds(userId, ids) > 0;
    }
}
