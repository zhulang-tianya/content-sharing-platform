package com.content.common.enums;

import com.content.common.result.IResultCode;

public enum UserStatus implements IResultCode {
    
    NORMAL(0, "正常"),
    
    DISABLE(1, "停用"),
    
    DELETED(2, "删除");
    
    private final int code;
    
    private final String message;
    
    UserStatus(int code, String message) {
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
