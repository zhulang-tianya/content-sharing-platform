package com.content.common.enums;

import com.content.common.result.IResultCode;

public enum ArticleStatus implements IResultCode {
    
    DRAFT(0, "草稿"),
    
    PUBLISHED(1, "已发布"),
    
    OFFLINE(2, "已下线"),
    
    DELETED(3, "已删除");
    
    private final int code;
    
    private final String message;
    
    ArticleStatus(int code, String message) {
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
