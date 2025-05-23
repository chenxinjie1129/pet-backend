package com.petshome.api.service.impl;

import com.petshome.api.common.Pagination;
import com.petshome.api.exception.BusinessException;
import com.petshome.api.model.entity.Product;
import com.petshome.api.repository.ProductMapper;
import com.petshome.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品服务实现类
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Product getById(Long id) {
        return productMapper.selectById(id);
    }

    @Override
    public List<Product> getAll() {
        return productMapper.selectAll();
    }

    @Override
    public List<Product> getByCategoryId(Long categoryId) {
        return productMapper.selectByCategoryId(categoryId);
    }

    @Override
    public List<Product> getByKeyword(String keyword) {
        return productMapper.selectByKeyword(keyword);
    }

    @Override
    public Map<String, Object> getByPage(Long categoryId, String keyword, Integer page, Integer size) {
        // 参数校验
        if (page == null || page < 1) {
            page = 1;
        }
        if (size == null || size < 1) {
            size = 10;
        }

        // 计算偏移量
        int offset = (page - 1) * size;

        // 查询数据
        List<Product> products = productMapper.selectByPage(categoryId, keyword, offset, size);

        // 查询总数
        int total = productMapper.countByCondition(categoryId, keyword);

        // 计算总页数
        int totalPage = (total + size - 1) / size;

        // 构建分页信息
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(page);
        pagination.setPagePieces(size);
        pagination.setTotalPage(totalPage);
        pagination.setTotalPieces(Long.valueOf(total));

        // 构建返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("products", products);
        result.put("pagination", pagination);

        return result;
    }

    @Override
    public List<Product> getHotProducts(Integer limit) {
        if (limit == null || limit < 1) {
            limit = 10;
        }
        return productMapper.selectHotProducts(limit);
    }

    @Override
    @Transactional
    public Product add(Product product) {
        // 设置默认值
        if (product.getSales() == null) {
            product.setSales(0);
        }
        if (product.getStatus() == null) {
            product.setStatus(1);
        }

        // 插入数据
        productMapper.insert(product);

        return product;
    }

    @Override
    @Transactional
    public Product update(Product product) {
        // 检查商品是否存在
        Product existingProduct = productMapper.selectById(product.getId());
        if (existingProduct == null) {
            throw new BusinessException("商品不存在");
        }

        // 更新数据
        productMapper.update(product);

        return productMapper.selectById(product.getId());
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        // 检查商品是否存在
        Product existingProduct = productMapper.selectById(id);
        if (existingProduct == null) {
            throw new BusinessException("商品不存在");
        }

        // 删除商品
        return productMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional
    public boolean updateStatus(Long id, Integer status) {
        // 检查商品是否存在
        Product existingProduct = productMapper.selectById(id);
        if (existingProduct == null) {
            throw new BusinessException("商品不存在");
        }

        // 更新状态
        return productMapper.updateStatus(id, status) > 0;
    }

    @Override
    @Transactional
    public boolean updateStock(Long id, Integer stock) {
        // 检查商品是否存在
        Product existingProduct = productMapper.selectById(id);
        if (existingProduct == null) {
            throw new BusinessException("商品不存在");
        }

        // 检查库存是否合法
        if (stock < 0) {
            throw new BusinessException("库存不能为负数");
        }

        // 更新库存
        return productMapper.updateStock(id, stock) > 0;
    }

    @Override
    @Transactional
    public boolean increaseSales(Long id, Integer sales) {
        // 检查商品是否存在
        Product existingProduct = productMapper.selectById(id);
        if (existingProduct == null) {
            throw new BusinessException("商品不存在");
        }

        // 检查销量增量是否合法
        if (sales < 0) {
            throw new BusinessException("销量增量不能为负数");
        }

        // 增加销量
        return productMapper.increaseSales(id, sales) > 0;
    }
}
