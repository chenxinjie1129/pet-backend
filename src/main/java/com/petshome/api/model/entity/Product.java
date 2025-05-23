package com.petshome.api.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品实体类
 */
@Data
public class Product implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 商品ID
     */
    private Long id;
    
    /**
     * 商品名称
     */
    private String name;
    
    /**
     * 分类ID
     */
    private Long categoryId;
    
    /**
     * 品牌
     */
    private String brand;
    
    /**
     * 价格
     */
    private BigDecimal price;
    
    /**
     * 原价
     */
    private BigDecimal originalPrice;
    
    /**
     * 库存
     */
    private Integer stock;
    
    /**
     * 销量
     */
    private Integer sales;
    
    /**
     * 主图
     */
    private String mainImage;
    
    /**
     * 相册，多个图片用逗号分隔
     */
    private String album;
    
    /**
     * 商品描述
     */
    private String description;
    
    /**
     * 商品详情
     */
    private String detail;
    
    /**
     * 状态：0-下架，1-上架
     */
    private Integer status;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
}
