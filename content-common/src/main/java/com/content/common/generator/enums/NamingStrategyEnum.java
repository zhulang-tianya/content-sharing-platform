package com.content.common.generator.enums;

public enum NamingStrategyEnum {
    
    underline_to_camel("underline_to_camel"),
    
    no_change("no_change");
    
    private final String value;
    
    NamingStrategyEnum(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static NamingStrategyEnum getByValue(String value) {
        for (NamingStrategyEnum strategy : values()) {
            if (strategy.getValue().equals(value)) {
                return strategy;
            }
        }
        return underline_to_camel;
    }
}
