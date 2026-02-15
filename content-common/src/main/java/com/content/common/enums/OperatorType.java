package com.content.common.enums;

import com.content.common.result.IResultCode;

public enum OperatorType implements IResultCode {
    
    OTHER(0, "其它"),
    
    MANAGE(1, "后台用户"),
    
    MOBILE(2, "手机端用户");
    
    private final int code;
    
    private final String message;
    
    OperatorType(int code, String message) {
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
