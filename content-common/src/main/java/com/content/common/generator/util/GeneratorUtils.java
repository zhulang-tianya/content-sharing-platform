package com.content.common.generator.util;

import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.content.common.generator.config.CodeGeneratorProperties;

public class GeneratorUtils {

    public static DateType getDateType(String dateType) {
        return switch (dateType.toUpperCase()) {
            case "ONLY_DATE" -> DateType.ONLY_DATE;
            case "SQL_PACK" -> DateType.SQL_PACK;
            case "TIME_PACK" -> DateType.TIME_PACK;
            default -> DateType.ONLY_DATE;
        };
    }

    public static NamingStrategy getNamingStrategy(String naming) {
        return switch (naming.toLowerCase()) {
            case "underline_to_camel" -> NamingStrategy.underline_to_camel;
            case "no_change" -> NamingStrategy.no_change;
            default -> NamingStrategy.underline_to_camel;
        };
    }

    public static String[] listToArray(java.util.List<String> list) {
        return list.toArray(new String[0]);
    }

    public static String getOutputDir(String baseDir, String... paths) {
        if (paths == null || paths.length == 0) {
            return baseDir;
        }
        StringBuilder sb = new StringBuilder(baseDir);
        for (String path : paths) {
            if (!path.isEmpty()) {
                sb.append("/").append(path);
            }
        }
        return sb.toString();
    }

    public static String getPackage(String parent, String... paths) {
        if (paths == null || paths.length == 0) {
            return parent;
        }
        StringBuilder sb = new StringBuilder(parent);
        for (String path : paths) {
            if (!path.isEmpty()) {
                sb.append(".").append(path);
            }
        }
        return sb.toString();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String getDefaultIfEmpty(String str, String defaultValue) {
        return isEmpty(str) ? defaultValue : str;
    }

    public static String[] getDefaultIfEmpty(String[] array, String[] defaultValue) {
        return array == null || array.length == 0 ? defaultValue : array;
    }

    public static boolean getDefaultIfEmpty(Boolean bool, boolean defaultValue) {
        return bool == null ? defaultValue : bool;
    }

    public static int getDefaultIfEmpty(Integer i, int defaultValue) {
        return i == null ? defaultValue : i;
    }

    public static long getDefaultIfEmpty(Long l, long defaultValue) {
        return l == null ? defaultValue : l;
    }

    public static double getDefaultIfEmpty(Double d, double defaultValue) {
        return d == null ? defaultValue : d;
    }

    public static float getDefaultIfEmpty(Float f, float defaultValue) {
        return f == null ? defaultValue : f;
    }

    public static CodeGeneratorProperties getDefaultProperties() {
        return new CodeGeneratorProperties();
    }
}
