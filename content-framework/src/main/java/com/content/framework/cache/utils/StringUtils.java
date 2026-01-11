package com.content.framework.cache.utils;

public class StringUtils {
    
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
    
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    
    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }
    
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
}
