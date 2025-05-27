package com.petshome.api.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品分类实体类
 */
@Data
public class ProductCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 排序值，越小越靠前
     */
    private Integer sort;

    /**
     * 图标
     */
    private String icon;

    /**
     * 状态：0-禁用，1-启用
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
