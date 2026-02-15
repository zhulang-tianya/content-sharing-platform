package com.content.common.generator.enums;

public enum DateTypeEnum {
    
    ONLY_DATE("ONLY_DATE"),
    
    SQL_PACK("SQL_PACK"),
    
    TIME_PACK("TIME_PACK");
    
    private final String value;
    
    DateTypeEnum(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static DateTypeEnum getByValue(String value) {
        for (DateTypeEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return ONLY_DATE;
    }
}
