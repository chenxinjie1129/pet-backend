package com.petshome.api.exception;

import com.petshome.api.common.ResultCode;
import lombok.Getter;

/**
 * 业务异常类
 */
@Getter
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误消息
     */
    private final String message;

    /**
     * 构造函数
     * @param message 错误消息
     */
    public BusinessException(String message) {
        this.code = ResultCode.ERROR.getCode();
        this.message = message;
    }

    /**
     * 构造函数
     * @param code 错误码
     * @param message 错误消息
     */
    public BusinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 构造函数
     * @param resultCode 结果枚举
     */
    public BusinessException(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    /**
     * 构造函数
     * @param resultCode 结果枚举
     * @param message 错误消息
     */
    public BusinessException(ResultCode resultCode, String message) {
        this.code = resultCode.getCode();
        this.message = message;
    }

    /**
     * 获取错误码
     * @return 错误码
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 获取错误消息
     * @return 错误消息
     */
    @Override
    public String getMessage() {
        return message;
    }
}
