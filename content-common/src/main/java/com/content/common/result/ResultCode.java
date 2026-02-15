package com.content.common.result;

/**
 * 响应码枚举类
 */
public enum ResultCode {
    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 请求参数错误
     */
    BAD_REQUEST(400, "请求参数错误"),

    /**
     * 未授权
     */
    UNAUTHORIZED(401, "未授权"),

    /**
     * 禁止访问
     */
    FORBIDDEN(403, "禁止访问"),

    /**
     * 资源不存在
     */
    NOT_FOUND(404, "资源不存在"),

    /**
     * 请求方法不允许
     */
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),

    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),

    /**
     * 服务不可用
     */
    SERVICE_UNAVAILABLE(503, "服务不可用"),

    /**
     * 数据库操作失败
     */
    DATABASE_ERROR(5001, "数据库操作失败"),

    /**
     * 数据验证失败
     */
    VALIDATION_ERROR(5002, "数据验证失败"),

    /**
     * 业务逻辑错误
     */
    BUSINESS_ERROR(5003, "业务逻辑错误"),

    /**
     * 网络请求失败
     */
    NETWORK_ERROR(5004, "网络请求失败"),

    /**
     * 文件操作失败
     */
    FILE_ERROR(5005, "文件操作失败");

    /**
     * 响应码
     */
    private final Integer code;

    /**
     * 响应消息
     */
    private final String message;

    /**
     * 构造方法
     *
     * @param code    响应码
     * @param message 响应消息
     */
    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取响应码
     *
     * @return 响应码
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 获取响应消息
     *
     * @return 响应消息
     */
    public String getMessage() {
        return message;
    }
}