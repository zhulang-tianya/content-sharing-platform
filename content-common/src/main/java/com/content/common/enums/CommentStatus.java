package com.content.common.enums;

import com.content.common.result.IResultCode;

public enum CommentStatus implements IResultCode {
    
    NORMAL(0, "正常"),
    
    AUDITING(1, "审核中"),
    
    REJECTED(2, "已拒绝"),
    
    DELETED(3, "已删除");
    
    private final int code;
    
    private final String message;
    
    CommentStatus(int code, String message) {
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
