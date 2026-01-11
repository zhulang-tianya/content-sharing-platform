package com.content.common.exception;

import com.content.common.result.IResultCode;

public class AuthException extends RuntimeException {

    private final int code;

    private final String message;

    public AuthException(String message) {
        super(message);
        this.code = 401;
        this.message = message;
    }

    public AuthException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public AuthException(IResultCode resultCode) {
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
