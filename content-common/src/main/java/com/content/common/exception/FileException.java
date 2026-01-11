package com.content.common.exception;

import com.content.common.result.IResultCode;

public class FileException extends RuntimeException {

    private final int code;

    private final String message;

    public FileException(String message) {
        super(message);
        this.code = 500;
        this.message = message;
    }

    public FileException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public FileException(IResultCode resultCode) {
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
