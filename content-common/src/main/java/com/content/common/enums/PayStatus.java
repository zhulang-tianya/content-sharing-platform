package com.content.common.enums;

import com.content.common.result.IResultCode;

public enum PayStatus implements IResultCode {
    
    UNPAID(0, "未支付"),
    
    PAID(1, "已支付"),
    
    REFUNDING(2, "退款中"),
    
    REFUNDED(3, "已退款"),
    
    CANCELLED(4, "已取消");
    
    private final int code;
    
    private final String message;
    
    PayStatus(int code, String message) {
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
