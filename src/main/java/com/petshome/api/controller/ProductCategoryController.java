package com.petshome.api.controller;

import com.petshome.api.common.ApiResponse;
import com.petshome.api.model.entity.ProductCategory;
import com.petshome.api.service.ProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品分类控制器
 */
@Api(tags = "商品分类接口")
@RestController
@RequestMapping("/mall/categories")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 获取所有商品分类
     */
    @ApiOperation("获取所有商品分类")
    @GetMapping
    public ApiResponse<List<ProductCategory>> getAllCategories() {
        List<ProductCategory> categories = productCategoryService.getAll();
        return ApiResponse.success(categories);
    }

    /**
     * 根据ID获取商品分类
     */
    @ApiOperation("根据ID获取商品分类")
    @GetMapping("/{id}")
    public ApiResponse<ProductCategory> getCategoryById(
            @ApiParam(value = "分类ID", required = true) @PathVariable Long id) {
        ProductCategory category = productCategoryService.getById(id);
        return ApiResponse.success(category);
    }



    /**
     * 添加商品分类
     */
    @ApiOperation("添加商品分类")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ProductCategory> addCategory(
            @ApiParam(value = "商品分类信息", required = true) @RequestBody ProductCategory category) {
        ProductCategory addedCategory = productCategoryService.add(category);
        return ApiResponse.success(addedCategory);
    }

    /**
     * 更新商品分类
     */
    @ApiOperation("更新商品分类")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ProductCategory> updateCategory(
            @ApiParam(value = "分类ID", required = true) @PathVariable Long id,
            @ApiParam(value = "商品分类信息", required = true) @RequestBody ProductCategory category) {
        category.setId(id);
        ProductCategory updatedCategory = productCategoryService.update(category);
        return ApiResponse.success(updatedCategory);
    }

    /**
     * 删除商品分类
     */
    @ApiOperation("删除商品分类")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Boolean> deleteCategory(
            @ApiParam(value = "分类ID", required = true) @PathVariable Long id) {
        boolean result = productCategoryService.delete(id);
        return ApiResponse.success(result);
    }

    /**
     * 更新商品分类状态
     */
    @ApiOperation("更新商品分类状态")
    @PutMapping("/{id}/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Boolean> updateCategoryStatus(
            @ApiParam(value = "分类ID", required = true) @PathVariable Long id,
            @ApiParam(value = "状态", required = true) @PathVariable Integer status) {
        boolean result = productCategoryService.updateStatus(id, status);
        return ApiResponse.success(result);
    }
}
