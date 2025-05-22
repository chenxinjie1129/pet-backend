package com.petshome.api.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 宠物实体类
 */
@Data
public class Pet implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 宠物ID
     */
    private Long id;
    
    /**
     * 宠物名称
     */
    private String name;
    
    /**
     * 宠物类型（1-猫，2-狗，3-兔子，4-其他）
     */
    private Integer type;
    
    /**
     * 宠物品种
     */
    private String breed;
    
    /**
     * 宠物性别（0-未知，1-公，2-母）
     */
    private Integer gender;
    
    /**
     * 宠物年龄（月）
     */
    private Integer age;
    
    /**
     * 宠物体重（克）
     */
    private Integer weight;
    
    /**
     * 是否绝育（0-否，1-是）
     */
    private Integer sterilized;
    
    /**
     * 宠物图片URL
     */
    private String avatar;
    
    /**
     * 所属用户ID
     */
    private Long userId;
    
    /**
     * 宠物状态（0-正常，1-生病，2-离世）
     */
    private Integer status;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
} 