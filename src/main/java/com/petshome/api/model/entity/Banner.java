package com.petshome.api.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 轮播图实体类
 */
@Data
public class Banner implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 轮播图ID
     */
    private Long id;
    
    /**
     * 标题
     */
    private String title;
    
    /**
     * 图片URL
     */
    private String imageUrl;
    
    /**
     * 链接URL
     */
    private String linkUrl;
    
    /**
     * 排序值，越小越靠前
     */
    private Integer sort;
    
    /**
     * 状态（0-禁用，1-启用）
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
