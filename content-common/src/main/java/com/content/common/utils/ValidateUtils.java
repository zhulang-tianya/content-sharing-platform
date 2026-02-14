package com.content.common.utils;

import java.util.Collection;
import java.util.Map;

import java.util.regex.Pattern;

public class ValidateUtils {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");

    private static final Pattern MOBILE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{3,4}-?\\d{7,8}$");

    private static final Pattern ID_CARD_PATTERN = Pattern.compile(
        "^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[\\dXx]$");

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{4,20}$");

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$");

    private static final Pattern URL_PATTERN = Pattern.compile(
        "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$");

    private static final Pattern IP_PATTERN = Pattern.compile(
        "^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$");

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^-?\\d+$");

    private static final Pattern DECIMAL_PATTERN = Pattern.compile("^-?\\d+\\.\\d+$");

    private static final Pattern CHINESE_PATTERN = Pattern.compile("^[\\u4e00-\\u9fa5]+$");

    private static final Pattern LETTER_PATTERN = Pattern.compile("^[a-zA-Z]+$");

    private static final Pattern DIGIT_PATTERN = Pattern.compile("^\\d+$");

    private static final Pattern ALPHANUMERIC_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");

    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    private static final Pattern DATETIME_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$");

    private static final Pattern TIME_PATTERN = Pattern.compile("^\\d{2}:\\d{2}:\\d{2}$");

    private static final Pattern POSTAL_CODE_PATTERN = Pattern.compile("^\\d{6}$");

    private static final Pattern BANK_CARD_PATTERN = Pattern.compile("^\\d{16,19}$");

    private static final Pattern CAR_LICENSE_PATTERN = Pattern.compile(
        "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}" +
        "[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$");

    private static final Pattern QQ_PATTERN = Pattern.compile("^[1-9]\\d{4,10}$");

    private static final Pattern WECHAT_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_-]{5,19}$");

    private static final Pattern WEIBO_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]{4,20}$");

    private static final Pattern ALIPAY_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$|^1[3-9]\\d{9}$");

    private static final Pattern TEL_PATTERN = Pattern.compile(
        "^1[3-9]\\d{9}$|^\\d{3,4}-?\\d{7,8}$");

    private static final Pattern PASSWORD_STRENGTH_PATTERN = Pattern.compile(
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");

    private static final Pattern CHINESE_NAME_PATTERN = Pattern.compile("^[\\u4e00-\\u9fa5]{2,8}$");

    private static final Pattern ENGLISH_NAME_PATTERN = Pattern.compile("^[A-Za-z ]{2,20}$");

    private static final Pattern BIRTHDAY_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    private static final Pattern AGE_PATTERN = Pattern.compile("^[1-9]\\d{0,2}$");

    private static final Pattern HEIGHT_PATTERN = Pattern.compile("^[1-2]\\d{2}(\\.\\d{1,2})?$");

    private static final Pattern WEIGHT_PATTERN = Pattern.compile(
        "^[3-9]\\d(\\.\\d{1,2})?$|^1[0-9]{2}(\\.\\d{1,2})?$|^2[0-9]{2}(\\.\\d{1,2})?$");

    public static boolean isEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        return MOBILE_PATTERN.matcher(mobile).matches();
    }

    public static boolean isPhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean isIdCard(String idCard) {
        if (StringUtils.isEmpty(idCard)) {
            return false;
        }
        if (!ID_CARD_PATTERN.matcher(idCard).matches()) {
            return false;
        }
        return validateIdCard(idCard);
    }

    public static boolean isUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            return false;
        }
        return USERNAME_PATTERN.matcher(username).matches();
    }

    public static boolean isPassword(String password) {
        if (StringUtils.isEmpty(password)) {
            return false;
        }
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    public static boolean isUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            return false;
        }
        return URL_PATTERN.matcher(url).matches();
    }

    public static boolean isIp(String ip) {
        if (StringUtils.isEmpty(ip)) {
            return false;
        }
        return IP_PATTERN.matcher(ip).matches();
    }

    public static boolean isNumber(String number) {
        if (StringUtils.isEmpty(number)) {
            return false;
        }
        return NUMBER_PATTERN.matcher(number).matches();
    }

    public static boolean isDecimal(String decimal) {
        if (StringUtils.isEmpty(decimal)) {
            return false;
        }
        return DECIMAL_PATTERN.matcher(decimal).matches();
    }

    public static boolean isChinese(String chinese) {
        if (StringUtils.isEmpty(chinese)) {
            return false;
        }
        return CHINESE_PATTERN.matcher(chinese).matches();
    }

    public static boolean isLetter(String letter) {
        if (StringUtils.isEmpty(letter)) {
            return false;
        }
        return LETTER_PATTERN.matcher(letter).matches();
    }

    public static boolean isDigit(String digit) {
        if (StringUtils.isEmpty(digit)) {
            return false;
        }
        return DIGIT_PATTERN.matcher(digit).matches();
    }

    public static boolean isAlphanumeric(String alphanumeric) {
        if (StringUtils.isEmpty(alphanumeric)) {
            return false;
        }
        return ALPHANUMERIC_PATTERN.matcher(alphanumeric).matches();
    }

    public static boolean isDate(String date) {
        if (StringUtils.isEmpty(date)) {
            return false;
        }
        return DATE_PATTERN.matcher(date).matches();
    }

    public static boolean isDatetime(String datetime) {
        if (StringUtils.isEmpty(datetime)) {
            return false;
        }
        return DATETIME_PATTERN.matcher(datetime).matches();
    }

    public static boolean isTime(String time) {
        if (StringUtils.isEmpty(time)) {
            return false;
        }
        return TIME_PATTERN.matcher(time).matches();
    }

    public static boolean isPostalCode(String postalCode) {
        if (StringUtils.isEmpty(postalCode)) {
            return false;
        }
        return POSTAL_CODE_PATTERN.matcher(postalCode).matches();
    }

    public static boolean isBankCard(String bankCard) {
        if (StringUtils.isEmpty(bankCard)) {
            return false;
        }
        return BANK_CARD_PATTERN.matcher(bankCard).matches();
    }

    public static boolean isCarLicense(String carLicense) {
        if (StringUtils.isEmpty(carLicense)) {
            return false;
        }
        return CAR_LICENSE_PATTERN.matcher(carLicense).matches();
    }

    public static boolean isQq(String qq) {
        if (StringUtils.isEmpty(qq)) {
            return false;
        }
        return QQ_PATTERN.matcher(qq).matches();
    }

    public static boolean isWechat(String wechat) {
        if (StringUtils.isEmpty(wechat)) {
            return false;
        }
        return WECHAT_PATTERN.matcher(wechat).matches();
    }

    public static boolean isWeibo(String weibo) {
        if (StringUtils.isEmpty(weibo)) {
            return false;
        }
        return WEIBO_PATTERN.matcher(weibo).matches();
    }

    public static boolean isAlipay(String alipay) {
        if (StringUtils.isEmpty(alipay)) {
            return false;
        }
        return ALIPAY_PATTERN.matcher(alipay).matches();
    }

    public static boolean isTel(String tel) {
        if (StringUtils.isEmpty(tel)) {
            return false;
        }
        return TEL_PATTERN.matcher(tel).matches();
    }

    public static boolean isStrongPassword(String password) {
        if (StringUtils.isEmpty(password)) {
            return false;
        }
        return PASSWORD_STRENGTH_PATTERN.matcher(password).matches();
    }

    public static boolean isChineseName(String name) {
        if (StringUtils.isEmpty(name)) {
            return false;
        }
        return CHINESE_NAME_PATTERN.matcher(name).matches();
    }

    public static boolean isEnglishName(String name) {
        if (StringUtils.isEmpty(name)) {
            return false;
        }
        return ENGLISH_NAME_PATTERN.matcher(name).matches();
    }

    public static boolean isBirthday(String birthday) {
        if (StringUtils.isEmpty(birthday)) {
            return false;
        }
        return BIRTHDAY_PATTERN.matcher(birthday).matches();
    }

    public static boolean isAge(String age) {
        if (StringUtils.isEmpty(age)) {
            return false;
        }
        return AGE_PATTERN.matcher(age).matches();
    }

    public static boolean isHeight(String height) {
        if (StringUtils.isEmpty(height)) {
            return false;
        }
        return HEIGHT_PATTERN.matcher(height).matches();
    }

    public static boolean isWeight(String weight) {
        if (StringUtils.isEmpty(weight)) {
            return false;
        }
        return WEIGHT_PATTERN.matcher(weight).matches();
    }

    private static boolean validateIdCard(String idCard) {
        if (idCard == null || idCard.length() != 18) {
            return false;
        }
        char[] chars = idCard.toCharArray();
        int[] weights = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        char[] codes = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            if (!Character.isDigit(chars[i])) {
                return false;
            }
            sum += (chars[i] - '0') * weights[i];
        }
        int mod = sum % 11;
        return chars[17] == codes[mod];
    }

    public static boolean isBlank(String str) {
        return StringUtils.isBlank(str);
    }

    public static boolean isNotBlank(String str) {
        return StringUtils.isNotBlank(str);
    }

    public static boolean isEmpty(String str) {
        return StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str) {
        return StringUtils.isNotEmpty(str);
    }

    public static boolean isEmpty(Object obj) {
        return obj == null;
    }

    public static boolean isNotEmpty(Object obj) {
        return obj != null;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return CollectionUtils.isEmpty(collection);
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return CollectionUtils.isNotEmpty(collection);
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return CollectionUtils.isEmpty(map);
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return CollectionUtils.isNotEmpty(map);
    }

    public static boolean isEmpty(Object[] array) {
        return CollectionUtils.isEmpty(array);
    }

    public static boolean isNotEmpty(Object[] array) {
        return CollectionUtils.isNotEmpty(array);
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    public static boolean isTrue(Boolean bool) {
        return Boolean.TRUE.equals(bool);
    }

    public static boolean isFalse(Boolean bool) {
        return Boolean.FALSE.equals(bool);
    }

    public static boolean isPositive(Integer num) {
        return num != null && num > 0;
    }

    public static boolean isNegative(Integer num) {
        return num != null && num < 0;
    }

    public static boolean isZero(Integer num) {
        return num != null && num == 0;
    }

    public static boolean isPositive(Long num) {
        return num != null && num > 0;
    }

    public static boolean isNegative(Long num) {
        return num != null && num < 0;
    }

    public static boolean isZero(Long num) {
        return num != null && num == 0;
    }

    public static boolean isPositive(Double num) {
        return num != null && num > 0;
    }

    public static boolean isNegative(Double num) {
        return num != null && num < 0;
    }

    public static boolean isZero(Double num) {
        return num != null && num == 0;
    }

    public static boolean isBetween(Integer num, Integer min, Integer max) {
        return num != null && min != null && max != null && num >= min && num <= max;
    }

    public static boolean isBetween(Long num, Long min, Long max) {
        return num != null && min != null && max != null && num >= min && num <= max;
    }

    public static boolean isBetween(Double num, Double min, Double max) {
        return num != null && min != null && max != null && num >= min && num <= max;
    }

    public static boolean isLength(String str, int min, int max) {
        if (str == null) {
            return min <= 0;
        }
        int length = str.length();
        return length >= min && length <= max;
    }

    public static boolean isMinLength(String str, int min) {
        if (str == null) {
            return min <= 0;
        }
        return str.length() >= min;
    }

    public static boolean isMaxLength(String str, int max) {
        if (str == null) {
            return true;
        }
        return str.length() <= max;
    }

    public static boolean isEquals(Object obj1, Object obj2) {
        if (obj1 == obj2) {
            return true;
        }
        if (obj1 == null || obj2 == null) {
            return false;
        }
        return obj1.equals(obj2);
    }

    public static boolean isNotEquals(Object obj1, Object obj2) {
        return !isEquals(obj1, obj2);
    }

    public static boolean isGreaterThan(Integer num1, Integer num2) {
        return num1 != null && num2 != null && num1 > num2;
    }

    public static boolean isLessThan(Integer num1, Integer num2) {
        return num1 != null && num2 != null && num1 < num2;
    }

    public static boolean isGreaterThanOrEquals(Integer num1, Integer num2) {
        return num1 != null && num2 != null && num1 >= num2;
    }

    public static boolean isLessThanOrEquals(Integer num1, Integer num2) {
        return num1 != null && num2 != null && num1 <= num2;
    }

    public static boolean isGreaterThan(Long num1, Long num2) {
        return num1 != null && num2 != null && num1 > num2;
    }

    public static boolean isLessThan(Long num1, Long num2) {
        return num1 != null && num2 != null && num1 < num2;
    }

    public static boolean isGreaterThanOrEquals(Long num1, Long num2) {
        return num1 != null && num2 != null && num1 >= num2;
    }

    public static boolean isLessThanOrEquals(Long num1, Long num2) {
        return num1 != null && num2 != null && num1 <= num2;
    }

    public static boolean isGreaterThan(Double num1, Double num2) {
        return num1 != null && num2 != null && num1 > num2;
    }

    public static boolean isLessThan(Double num1, Double num2) {
        return num1 != null && num2 != null && num1 < num2;
    }

    public static boolean isGreaterThanOrEquals(Double num1, Double num2) {
        return num1 != null && num2 != null && num1 >= num2;
    }

    public static boolean isLessThanOrEquals(Double num1, Double num2) {
        return num1 != null && num2 != null && num1 <= num2;
    }
}
