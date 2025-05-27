package com.petshome.api.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一API响应结果封装
 */
@Data
@NoArgsConstructor
@ApiModel(description = "统一API响应结果封装")
public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码", example = "200", required = true, position = 1)
    private Integer code;

    /**
     * 消息
     */
    @ApiModelProperty(value = "消息", example = "操作成功", required = true, position = 2)
    private String message;

    /**
     * 数据
     */
    @ApiModelProperty(value = "数据", position = 3)
    private T data;

    /**
     * 分页信息
     */
    @ApiModelProperty(value = "分页信息", position = 4)
    private Pagination pagination;

    /**
     * 获取状态码
     * @return 状态码
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 设置状态码
     * @param code 状态码
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 获取消息
     * @return 消息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置消息
     * @param message 消息
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取数据
     * @return 数据
     */
    public T getData() {
        return data;
    }

    /**
     * 设置数据
     * @param data 数据
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * 获取分页信息
     * @return 分页信息
     */
    public Pagination getPagination() {
        return pagination;
    }

    /**
     * 设置分页信息
     * @param pagination 分页信息
     */
    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    /**
     * 构造函数
     * @param code 状态码
     * @param message 消息
     * @param data 数据
     */
    public ApiResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 构造函数
     * @param code 状态码
     * @param message 消息
     * @param data 数据
     * @param pagination 分页信息
     */
    public ApiResponse(Integer code, String message, T data, Pagination pagination) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.pagination = pagination;
    }

    /**
     * 成功返回
     * @param <T> 数据类型
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    /**
     * 成功返回（带数据）
     * @param data 数据
     * @param <T> 数据类型
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回（带消息和数据）
     * @param message 消息
     * @param data 数据
     * @param <T> 数据类型
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> successWithMsg(String message, T data) {
        return new ApiResponse<>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 成功返回（带分页）
     * @param data 数据
     * @param pagination 分页信息
     * @param <T> 数据类型
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> success(T data, Pagination pagination) {
        return new ApiResponse<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data, pagination);
    }

    /**
     * 失败返回
     * @param <T> 数据类型
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> error() {
        return new ApiResponse<>(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMessage(), null);
    }

    /**
     * 失败返回（带消息）
     * @param message 消息
     * @param <T> 数据类型
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(ResultCode.ERROR.getCode(), message, null);
    }

    /**
     * 失败返回（带状态码和消息）
     * @param code 状态码
     * @param message 消息
     * @param <T> 数据类型
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> error(Integer code, String message) {
        return new ApiResponse<>(code, message, null);
    }

    /**
     * 失败返回（ResultCode枚举）
     * @param resultCode 结果枚举
     * @param <T> 数据类型
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> error(ResultCode resultCode) {
        return new ApiResponse<>(resultCode.getCode(), resultCode.getMessage(), null);
    }
}