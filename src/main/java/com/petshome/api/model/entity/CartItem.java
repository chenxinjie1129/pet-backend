package com.petshome.api.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 购物车项实体类
 */
@Data
public class CartItem implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 购物车项ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 商品ID
     */
    private Long productId;
    
    /**
     * 数量
     */
    private Integer quantity;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
    
    /**
     * 关联的商品信息（非数据库字段）
     */
    private Product product;
}
