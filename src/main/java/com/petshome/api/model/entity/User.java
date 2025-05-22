package com.petshome.api.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 */
@Data
@ApiModel(description = "用户实体类")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", example = "1", required = false, position = 1)
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", example = "testuser", required = true, position = 2)
    private String username;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", example = "13800138000", required = true, position = 3)
    private String mobile;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", example = "password123", required = true, position = 4)
    private String password;

    /**
     * 盐值
     */
    @ApiModelProperty(value = "盐值", hidden = true)
    private String salt;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称", example = "测试用户", required = false, position = 5)
    private String nickname;

    /**
     * 头像URL
     */
    @ApiModelProperty(value = "头像URL", example = "https://example.com/avatar.jpg", required = false, position = 6)
    private String avatar;

    /**
     * 性别（0-未知，1-男，2-女）
     */
    @ApiModelProperty(value = "性别（0-未知，1-男，2-女）", example = "1", required = false, position = 7)
    private Integer gender;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱", example = "test@example.com", required = false, position = 8)
    private String email;

    /**
     * 账号状态（0-禁用，1-启用）
     */
    private Integer status;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}