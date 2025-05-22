package com.petshome.api.common;

import lombok.Getter;

/**
 * API 响应码枚举
 */
@Getter
public enum ResultCode {

    // 成功
    SUCCESS(200, "操作成功"),

    // 通用错误
    ERROR(500, "服务器内部错误"),

    // HTTP状态码
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),

    // 参数错误 1000~1999
    PARAM_ERROR(1000, "参数错误"),
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),

    // 用户错误 2000~2999
    USER_NOT_LOGIN(2001, "用户未登录"),
    USER_ACCOUNT_EXPIRED(2002, "账号已过期"),
    USER_CREDENTIALS_ERROR(2003, "密码错误"),
    USER_CREDENTIALS_EXPIRED(2004, "密码过期"),
    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    USER_ACCOUNT_LOCKED(2006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(2008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(2009, "账号下线"),

    // 业务错误 3000~3999
    NO_PERMISSION(3001, "没有权限"),
    VERIFICATION_CODE_ERROR(3002, "验证码错误"),

    // 文件上传 4000~4999
    FILE_NOT_FOUND(4001, "文件未找到"),
    FILE_TOO_LARGE(4002, "文件太大"),
    FILE_UPLOAD_ERROR(4003, "文件上传错误");

    /**
     * 响应码
     */
    private final Integer code;

    /**
     * 响应消息
     */
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}