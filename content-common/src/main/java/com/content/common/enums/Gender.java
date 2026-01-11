package com.content.common.enums;

import com.content.common.result.IResultCode;

public enum Gender implements IResultCode {
    
    MALE(0, "男"),
    
    FEMALE(1, "女"),
    
    UNKNOWN(2, "未知");
    
    private final int code;
    
    private final String message;
    
    Gender(int code, String message) {
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
