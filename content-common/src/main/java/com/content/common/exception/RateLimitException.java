package com.content.common.exception;

import com.content.common.result.IResultCode;

public class RateLimitException extends RuntimeException {

    private final int code;

    private final String message;

    public RateLimitException(String message) {
        super(message);
        this.code = 429;
        this.message = message;
    }

    public RateLimitException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public RateLimitException(IResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
