package com.petshome.api.controller;

import com.petshome.api.common.ApiResponse;
import com.petshome.api.model.entity.CartItem;
import com.petshome.api.model.entity.User;
import com.petshome.api.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 购物车控制器
 */
@Api(tags = "购物车接口")
@RestController
@RequestMapping("/mall/cart")
@PreAuthorize("isAuthenticated()")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 获取购物车信息
     */
    @ApiOperation("获取购物车信息")
    @GetMapping
    public ApiResponse<Map<String, Object>> getCartInfo() {
        Long userId = getCurrentUserId();
        Map<String, Object> cartInfo = cartService.getCartInfo(userId);
        return ApiResponse.success(cartInfo);
    }

    /**
     * 添加商品到购物车
     */
    @ApiOperation("添加商品到购物车")
    @PostMapping("/add")
    public ApiResponse<CartItem> addToCart(
            @ApiParam(value = "商品ID", required = true) @RequestParam Long productId,
            @ApiParam(value = "数量", required = true) @RequestParam Integer quantity) {
        Long userId = getCurrentUserId();
        CartItem cartItem = cartService.addToCart(userId, productId, quantity);
        return ApiResponse.success(cartItem);
    }

    /**
     * 更新购物车项数量
     */
    @ApiOperation("更新购物车项数量")
    @PutMapping("/{id}")
    public ApiResponse<CartItem> updateCartItem(
            @ApiParam(value = "购物车项ID", required = true) @PathVariable Long id,
            @ApiParam(value = "数量", required = true) @RequestParam Integer quantity) {
        Long userId = getCurrentUserId();
        CartItem cartItem = cartService.updateQuantity(id, userId, quantity);
        return ApiResponse.success(cartItem);
    }

    /**
     * 删除购物车项
     */
    @ApiOperation("删除购物车项")
    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteCartItem(
            @ApiParam(value = "购物车项ID", required = true) @PathVariable Long id) {
        Long userId = getCurrentUserId();
        boolean result = cartService.delete(id, userId);
        return ApiResponse.success(result);
    }

    /**
     * 清空购物车
     */
    @ApiOperation("清空购物车")
    @DeleteMapping("/clear")
    public ApiResponse<Boolean> clearCart() {
        Long userId = getCurrentUserId();
        boolean result = cartService.clear(userId);
        return ApiResponse.success(result);
    }

    /**
     * 批量删除购物车项
     */
    @ApiOperation("批量删除购物车项")
    @DeleteMapping("/batch")
    public ApiResponse<Boolean> batchDeleteCartItems(
            @ApiParam(value = "购物车项ID列表", required = true) @RequestBody List<Long> ids) {
        Long userId = getCurrentUserId();
        boolean result = cartService.batchDelete(ids, userId);
        return ApiResponse.success(result);
    }

    /**
     * 获取当前登录用户ID
     */
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return user.getId();
        }
        return null;
    }
}
