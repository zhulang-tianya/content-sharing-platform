package com.content.common.enums;

import com.content.common.result.IResultCode;

public enum YesNo implements IResultCode {
    
    NO(0, "否"),
    
    YES(1, "是");
    
    private final int code;
    
    private final String message;
    
    YesNo(int code, String message) {
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
