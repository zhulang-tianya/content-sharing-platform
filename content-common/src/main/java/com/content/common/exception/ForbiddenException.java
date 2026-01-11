package com.content.common.exception;

import com.content.common.result.IResultCode;

public class ForbiddenException extends RuntimeException {

    private final int code;

    private final String message;

    public ForbiddenException(String message) {
        super(message);
        this.code = 403;
        this.message = message;
    }

    public ForbiddenException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ForbiddenException(IResultCode resultCode) {
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
