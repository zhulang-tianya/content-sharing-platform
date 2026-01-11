package com.content.common.enums;

import com.content.common.result.IResultCode;

public enum BusinessType implements IResultCode {
    
    OTHER(0, "其它"),
    
    INSERT(1, "新增"),
    
    UPDATE(2, "修改"),
    
    DELETE(3, "删除"),
    
    GRANT(4, "授权"),
    
    EXPORT(5, "导出"),
    
    IMPORT(6, "导入"),
    
    FORCE(7, "强退"),
    
    GENCODE(8, "生成代码"),
    
    CLEAN(9, "清空数据");
    
    private final int code;
    
    private final String message;
    
    BusinessType(int code, String message) {
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
