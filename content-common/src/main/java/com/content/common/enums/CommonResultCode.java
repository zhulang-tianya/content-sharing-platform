package com.content.common.enums;

import com.content.common.result.IResultCode;

public enum CommonResultCode implements IResultCode {
    
    SUCCESS(200, "操作成功"),
    
    FAIL(500, "操作失败"),
    
    PARAM_ERROR(400, "参数错误"),
    
    UNAUTHORIZED(401, "未授权"),
    
    FORBIDDEN(403, "禁止访问"),
    
    NOT_FOUND(404, "资源不存在"),
    
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    
    VALIDATION_ERROR(422, "参数校验失败"),
    
    LOGIN_AGAIN(401, "请重新登录"),
    
    TOKEN_EXPIRED(401, "登录已过期，请重新登录"),
    
    TOKEN_INVALID(401, "登录状态已失效，请重新登录"),
    
    CAPTCHA_ERROR(400, "验证码错误"),
    
    CAPTCHA_EXPIRED(400, "验证码已失效"),
    
    USERNAME_EXIST(400, "用户名已存在"),
    
    PASSWORD_ERROR(400, "密码错误"),
    
    ACCOUNT_LOCKED(403, "账号已被锁定"),
    
    ACCOUNT_DISABLED(403, "账号已被禁用"),
    
    ACCOUNT_EXPIRED(403, "账号已过期"),
    
    FILE_UPLOAD_ERROR(500, "文件上传失败"),
    
    FILE_TYPE_ERROR(400, "文件类型错误"),
    
    FILE_SIZE_ERROR(400, "文件大小超出限制"),
    
    DATA_NOT_FOUND(404, "数据不存在"),
    
    DATA_EXIST(400, "数据已存在"),
    
    DATA_DELETE_SUCCESS(200, "删除成功"),
    
    DATA_UPDATE_SUCCESS(200, "更新成功"),
    
    DATA_SAVE_SUCCESS(200, "保存成功"),
    
    RATE_LIMIT_ERROR(429, "请求过于频繁，请稍后再试"),
    
    SERVICE_UNAVAILABLE(503, "服务不可用");
    
    private final int code;
    
    private final String message;
    
    CommonResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
    
    @Override
    public int getCode() {
        return code;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}
