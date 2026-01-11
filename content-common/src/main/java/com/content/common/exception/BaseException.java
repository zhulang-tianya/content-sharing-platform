package com.content.common.exception;

import com.content.common.result.ResultCode;

/**
 * 基础异常类
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 构造方法
     */
    public BaseException() {
        super();
    }

    /**
     * 构造方法
     *
     * @param message 错误信息
     */
    public BaseException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * 构造方法
     *
     * @param message 错误信息
     * @param cause   异常原因
     */
    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    /**
     * 构造方法
     *
     * @param code    错误码
     * @param message 错误信息
     */
    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 构造方法
     *
     * @param code    错误码
     * @param message 错误信息
     * @param cause   异常原因
     */
    public BaseException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    /**
     * 构造方法
     *
     * @param resultCode 结果码枚举
     */
    public BaseException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    /**
     * 构造方法
     *
     * @param resultCode 结果码枚举
     * @param cause      异常原因
     */
    public BaseException(ResultCode resultCode, Throwable cause) {
        super(resultCode.getMessage(), cause);
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 设置错误码
     *
     * @param code 错误码
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 获取错误信息
     *
     * @return 错误信息
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 设置错误信息
     *
     * @param message 错误信息
     */
    public void setMessage(String message) {
        this.message = message;
    }
}