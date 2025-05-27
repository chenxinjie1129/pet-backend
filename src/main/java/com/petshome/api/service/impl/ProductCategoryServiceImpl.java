package com.petshome.api.service.impl;

import com.petshome.api.exception.BusinessException;
import com.petshome.api.model.entity.ProductCategory;
import com.petshome.api.repository.ProductCategoryMapper;
import com.petshome.api.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品分类服务实现类
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public ProductCategory getById(Long id) {
        return productCategoryMapper.selectById(id);
    }

    @Override
    public List<ProductCategory> getAll() {
        return productCategoryMapper.selectAll();
    }



    @Override
    @Transactional
    public ProductCategory add(ProductCategory category) {
        // 设置默认值
        if (category.getSort() == null) {
            category.setSort(0);
        }
        if (category.getStatus() == null) {
            category.setStatus(1);
        }

        // 插入数据
        productCategoryMapper.insert(category);

        return category;
    }

    @Override
    @Transactional
    public ProductCategory update(ProductCategory category) {
        // 检查分类是否存在
        ProductCategory existingCategory = productCategoryMapper.selectById(category.getId());
        if (existingCategory == null) {
            throw new BusinessException("分类不存在");
        }

        // 更新数据
        productCategoryMapper.update(category);

        return productCategoryMapper.selectById(category.getId());
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        // 检查分类是否存在
        ProductCategory existingCategory = productCategoryMapper.selectById(id);
        if (existingCategory == null) {
            throw new BusinessException("分类不存在");
        }

        // 删除分类
        return productCategoryMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional
    public boolean updateStatus(Long id, Integer status) {
        // 检查分类是否存在
        ProductCategory existingCategory = productCategoryMapper.selectById(id);
        if (existingCategory == null) {
            throw new BusinessException("分类不存在");
        }

        // 更新状态
        return productCategoryMapper.updateStatus(id, status) > 0;
    }
}
