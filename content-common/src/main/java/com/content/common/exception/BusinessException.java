package com.content.common.exception;

import com.content.common.result.ResultCode;

/**
 * 业务异常类
 */
public class BusinessException extends BaseException {
    private static final long serialVersionUID = 1L;

    /**
     * 构造方法
     */
    public BusinessException() {
        super();
    }

    /**
     * 构造方法
     *
     * @param message 错误信息
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * 构造方法
     *
     * @param message 错误信息
     * @param cause   异常原因
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造方法
     *
     * @param code    错误码
     * @param message 错误信息
     */
    public BusinessException(Integer code, String message) {
        super(code, message);
    }

    /**
     * 构造方法
     *
     * @param code    错误码
     * @param message 错误信息
     * @param cause   异常原因
     */
    public BusinessException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }

    /**
     * 构造方法
     *
     * @param resultCode 结果码枚举
     */
    public BusinessException(ResultCode resultCode) {
        super(resultCode);
    }

    /**
     * 构造方法
     *
     * @param resultCode 结果码枚举
     * @param cause      异常原因
     */
    public BusinessException(ResultCode resultCode, Throwable cause) {
        super(resultCode, cause);
    }
}