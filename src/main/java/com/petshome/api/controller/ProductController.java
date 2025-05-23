package com.petshome.api.controller;

import com.petshome.api.common.ApiResponse;
import com.petshome.api.model.entity.Product;
import com.petshome.api.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 商品控制器
 */
@Api(tags = "商品接口")
@RestController
@RequestMapping("/mall/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 分页查询商品
     */
    @ApiOperation("分页查询商品")
    @GetMapping
    public ApiResponse<Map<String, Object>> getProducts(
            @ApiParam(value = "分类ID") @RequestParam(required = false) Long categoryId,
            @ApiParam(value = "关键词") @RequestParam(required = false) String keyword,
            @ApiParam(value = "页码", defaultValue = "1") @RequestParam(defaultValue = "1") Integer page,
            @ApiParam(value = "每页数量", defaultValue = "10") @RequestParam(defaultValue = "10") Integer size) {
        Map<String, Object> result = productService.getByPage(categoryId, keyword, page, size);
        return ApiResponse.success(result);
    }

    /**
     * 根据ID获取商品
     */
    @ApiOperation("根据ID获取商品")
    @GetMapping("/{id}")
    public ApiResponse<Product> getProductById(
            @ApiParam(value = "商品ID", required = true) @PathVariable Long id) {
        Product product = productService.getById(id);
        return ApiResponse.success(product);
    }

    /**
     * 获取热门商品
     */
    @ApiOperation("获取热门商品")
    @GetMapping("/hot")
    public ApiResponse<List<Product>> getHotProducts(
            @ApiParam(value = "数量", defaultValue = "5") @RequestParam(defaultValue = "5") Integer limit) {
        List<Product> products = productService.getHotProducts(limit);
        return ApiResponse.success(products);
    }

    /**
     * 根据分类ID获取商品
     */
    @ApiOperation("根据分类ID获取商品")
    @GetMapping("/category/{categoryId}")
    public ApiResponse<List<Product>> getProductsByCategoryId(
            @ApiParam(value = "分类ID", required = true) @PathVariable Long categoryId) {
        List<Product> products = productService.getByCategoryId(categoryId);
        return ApiResponse.success(products);
    }

    /**
     * 根据关键词搜索商品
     */
    @ApiOperation("根据关键词搜索商品")
    @GetMapping("/search")
    public ApiResponse<List<Product>> searchProducts(
            @ApiParam(value = "关键词", required = true) @RequestParam String keyword) {
        List<Product> products = productService.getByKeyword(keyword);
        return ApiResponse.success(products);
    }

    /**
     * 添加商品
     */
    @ApiOperation("添加商品")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Product> addProduct(
            @ApiParam(value = "商品信息", required = true) @RequestBody Product product) {
        Product addedProduct = productService.add(product);
        return ApiResponse.success(addedProduct);
    }

    /**
     * 更新商品
     */
    @ApiOperation("更新商品")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Product> updateProduct(
            @ApiParam(value = "商品ID", required = true) @PathVariable Long id,
            @ApiParam(value = "商品信息", required = true) @RequestBody Product product) {
        product.setId(id);
        Product updatedProduct = productService.update(product);
        return ApiResponse.success(updatedProduct);
    }

    /**
     * 删除商品
     */
    @ApiOperation("删除商品")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Boolean> deleteProduct(
            @ApiParam(value = "商品ID", required = true) @PathVariable Long id) {
        boolean result = productService.delete(id);
        return ApiResponse.success(result);
    }

    /**
     * 更新商品状态
     */
    @ApiOperation("更新商品状态")
    @PutMapping("/{id}/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Boolean> updateProductStatus(
            @ApiParam(value = "商品ID", required = true) @PathVariable Long id,
            @ApiParam(value = "状态", required = true) @PathVariable Integer status) {
        boolean result = productService.updateStatus(id, status);
        return ApiResponse.success(result);
    }

    /**
     * 更新商品库存
     */
    @ApiOperation("更新商品库存")
    @PutMapping("/{id}/stock/{stock}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Boolean> updateProductStock(
            @ApiParam(value = "商品ID", required = true) @PathVariable Long id,
            @ApiParam(value = "库存", required = true) @PathVariable Integer stock) {
        boolean result = productService.updateStock(id, stock);
        return ApiResponse.success(result);
    }
}
