package com.content.common.enums;

import com.content.common.result.IResultCode;

public enum OrderStatus implements IResultCode {
    
    PENDING(0, "待处理"),
    
    PROCESSING(1, "处理中"),
    
    COMPLETED(2, "已完成"),
    
    CANCELLED(3, "已取消"),
    
    REFUNDED(4, "已退款");
    
    private final int code;
    
    private final String message;
    
    OrderStatus(int code, String message) {
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
