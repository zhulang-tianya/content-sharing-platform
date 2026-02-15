package com.content.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {

    public static final int DEFAULT_SCALE = 2;

    public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;

    public static int add(int a, int b) {
        return a + b;
    }

    public static long add(long a, long b) {
        return a + b;
    }

    public static double add(double a, double b) {
        return BigDecimal.valueOf(a).add(BigDecimal.valueOf(b)).doubleValue();
    }

    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        if (a == null) {
            a = BigDecimal.ZERO;
        }
        if (b == null) {
            b = BigDecimal.ZERO;
        }
        return a.add(b);
    }

    public static int subtract(int a, int b) {
        return a - b;
    }

    public static long subtract(long a, long b) {
        return a - b;
    }

    public static double subtract(double a, double b) {
        return BigDecimal.valueOf(a).subtract(BigDecimal.valueOf(b)).doubleValue();
    }

    public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
        if (a == null) {
            a = BigDecimal.ZERO;
        }
        if (b == null) {
            b = BigDecimal.ZERO;
        }
        return a.subtract(b);
    }

    public static int multiply(int a, int b) {
        return a * b;
    }

    public static long multiply(long a, long b) {
        return a * b;
    }

    public static double multiply(double a, double b) {
        return BigDecimal.valueOf(a).multiply(BigDecimal.valueOf(b)).doubleValue();
    }

    public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
        if (a == null) {
            a = BigDecimal.ZERO;
        }
        if (b == null) {
            b = BigDecimal.ZERO;
        }
        return a.multiply(b);
    }

    public static double divide(double a, double b) {
        return divide(a, b, DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
    }

    public static double divide(double a, double b, int scale) {
        return divide(a, b, scale, DEFAULT_ROUNDING_MODE);
    }

    public static double divide(double a, double b, int scale, RoundingMode roundingMode) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return BigDecimal.valueOf(a).divide(BigDecimal.valueOf(b), scale, roundingMode).doubleValue();
    }

    public static BigDecimal divide(BigDecimal a, BigDecimal b) {
        return divide(a, b, DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
    }

    public static BigDecimal divide(BigDecimal a, BigDecimal b, int scale) {
        return divide(a, b, scale, DEFAULT_ROUNDING_MODE);
    }

    public static BigDecimal divide(BigDecimal a, BigDecimal b, int scale, RoundingMode roundingMode) {
        if (a == null) {
            a = BigDecimal.ZERO;
        }
        if (b == null) {
            b = BigDecimal.ZERO;
        }
        if (b.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return a.divide(b, scale, roundingMode);
    }

    public static double round(double value) {
        return round(value, DEFAULT_SCALE);
    }

    public static double round(double value, int scale) {
        return round(value, scale, DEFAULT_ROUNDING_MODE);
    }

    public static double round(double value, int scale, RoundingMode roundingMode) {
        return BigDecimal.valueOf(value).setScale(scale, roundingMode).doubleValue();
    }

    public static BigDecimal round(BigDecimal value) {
        return round(value, DEFAULT_SCALE);
    }

    public static BigDecimal round(BigDecimal value, int scale) {
        return round(value, scale, DEFAULT_ROUNDING_MODE);
    }

    public static BigDecimal round(BigDecimal value, int scale, RoundingMode roundingMode) {
        if (value == null) {
            return null;
        }
        return value.setScale(scale, roundingMode);
    }

    public static int max(int a, int b) {
        return Math.max(a, b);
    }

    public static long max(long a, long b) {
        return Math.max(a, b);
    }

    public static double max(double a, double b) {
        return Math.max(a, b);
    }

    public static int min(int a, int b) {
        return Math.min(a, b);
    }

    public static long min(long a, long b) {
        return Math.min(a, b);
    }

    public static double min(double a, double b) {
        return Math.min(a, b);
    }

    public static int abs(int a) {
        return Math.abs(a);
    }

    public static long abs(long a) {
        return Math.abs(a);
    }

    public static double abs(double a) {
        return Math.abs(a);
    }

    public static double pow(double a, double b) {
        return Math.pow(a, b);
    }

    public static double sqrt(double a) {
        return Math.sqrt(a);
    }

    public static int randomInt(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public static double randomDouble(double min, double max) {
        return Math.random() * (max - min) + min;
    }

    public static boolean isZero(BigDecimal value) {
        if (value == null) {
            return true;
        }
        return value.compareTo(BigDecimal.ZERO) == 0;
    }

    public static boolean isPositive(BigDecimal value) {
        if (value == null) {
            return false;
        }
        return value.compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean isNegative(BigDecimal value) {
        if (value == null) {
            return false;
        }
        return value.compareTo(BigDecimal.ZERO) < 0;
    }

    public static boolean equals(BigDecimal a, BigDecimal b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.compareTo(b) == 0;
    }

    public static int compare(BigDecimal a, BigDecimal b) {
        if (a == null && b == null) {
            return 0;
        }
        if (a == null) {
            return -1;
        }
        if (b == null) {
            return 1;
        }
        return a.compareTo(b);
    }

    public static BigDecimal toBigDecimal(Integer value) {
        return value == null ? null : BigDecimal.valueOf(value);
    }

    public static BigDecimal toBigDecimal(Long value) {
        return value == null ? null : BigDecimal.valueOf(value);
    }

    public static BigDecimal toBigDecimal(Double value) {
        return value == null ? null : BigDecimal.valueOf(value);
    }

    public static BigDecimal toBigDecimal(String value) {
        return StringUtils.isEmpty(value) ? null : new BigDecimal(value);
    }

    public static Integer toInteger(BigDecimal value) {
        return value == null ? null : value.intValue();
    }

    public static Long toLong(BigDecimal value) {
        return value == null ? null : value.longValue();
    }

    public static Double toDouble(BigDecimal value) {
        return value == null ? null : value.doubleValue();
    }

    public static String toString(BigDecimal value) {
        return value == null ? null : value.toPlainString();
    }

    public static BigDecimal percent(BigDecimal value, BigDecimal percent) {
        if (value == null || percent == null) {
            return null;
        }
        return multiply(value, divide(percent, new BigDecimal("100")));
    }

    public static BigDecimal addPercent(BigDecimal value, BigDecimal percent) {
        if (value == null || percent == null) {
            return null;
        }
        return add(value, percent(value, percent));
    }

    public static BigDecimal subtractPercent(BigDecimal value, BigDecimal percent) {
        if (value == null || percent == null) {
            return null;
        }
        return subtract(value, percent(value, percent));
    }

    public static BigDecimal average(BigDecimal[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal value : values) {
            if (value != null) {
                sum = add(sum, value);
            }
        }
        return divide(sum, new BigDecimal(values.length));
    }

    public static BigDecimal sum(BigDecimal[] values) {
        if (values == null || values.length == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal value : values) {
            if (value != null) {
                sum = add(sum, value);
            }
        }
        return sum;
    }

    public static int sum(int[] values) {
        if (values == null || values.length == 0) {
            return 0;
        }
        int sum = 0;
        for (int value : values) {
            sum += value;
        }
        return sum;
    }

    public static long sum(long[] values) {
        if (values == null || values.length == 0) {
            return 0;
        }
        long sum = 0;
        for (long value : values) {
            sum += value;
        }
        return sum;
    }

    public static double sum(double[] values) {
        if (values == null || values.length == 0) {
            return 0;
        }
        double sum = 0;
        for (double value : values) {
            sum += value;
        }
        return sum;
    }
}
