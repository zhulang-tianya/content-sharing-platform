package com.content.common.exception;

import com.content.common.result.IResultCode;

public class NotFoundException extends RuntimeException {

    private final int code;

    private final String message;

    public NotFoundException(String message) {
        super(message);
        this.code = 404;
        this.message = message;
    }

    public NotFoundException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public NotFoundException(IResultCode resultCode) {
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
