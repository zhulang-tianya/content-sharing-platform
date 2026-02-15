package com.content.common.utils;

import java.util.Random;
import java.util.UUID;
import org.mindrot.jbcrypt.BCrypt;
import org.apache.commons.codec.digest.DigestUtils;

public class EncryptUtils {

    private static final String SALT_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    
    private static final Random RANDOM = new Random();
    
    public static String generateSalt(int length) {
        StringBuilder salt = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            salt.append(SALT_CHARS.charAt(RANDOM.nextInt(SALT_CHARS.length())));
        }
        return salt.toString();
    }
    
    public static String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    
    public static String encryptPassword(String password, String salt) {
        return BCrypt.hashpw(password, salt);
    }
    
    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
    
    public static String generateMD5(String str) {
        return DigestUtils.md5Hex(str);
    }
    
    public static String generateSHA1(String str) {
        return DigestUtils.sha1Hex(str);
    }
    
    public static String generateSHA256(String str) {
        return DigestUtils.sha256Hex(str);
    }
    
    public static String generateSHA512(String str) {
        return DigestUtils.sha512Hex(str);
    }
    
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    
    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(SALT_CHARS.charAt(RANDOM.nextInt(SALT_CHARS.length())));
        }
        return sb.toString();
    }
    
    public static String generateRandomNumber(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(RANDOM.nextInt(10));
        }
        return sb.toString();
    }
    
    public static String maskString(String str, int start, int end) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        int length = str.length();
        if (start >= length || end <= 0 || start >= end) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        for (int i = start; i < end && i < length; i++) {
            sb.setCharAt(i, '*');
        }
        return sb.toString();
    }
    
    public static String maskMobile(String mobile) {
        if (StringUtils.isEmpty(mobile) || mobile.length() < 11) {
            return mobile;
        }
        return maskString(mobile, 3, 7);
    }
    
    public static String maskEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            return email;
        }
        int atIndex = email.indexOf('@');
        if (atIndex <= 2) {
            return email;
        }
        return maskString(email, 1, atIndex) + email.substring(atIndex);
    }
    
    public static String maskIdCard(String idCard) {
        if (StringUtils.isEmpty(idCard) || idCard.length() < 15) {
            return idCard;
        }
        return maskString(idCard, 6, idCard.length() - 4);
    }
    
    public static String maskBankCard(String bankCard) {
        if (StringUtils.isEmpty(bankCard) || bankCard.length() < 16) {
            return bankCard;
        }
        return maskString(bankCard, 4, bankCard.length() - 4);
    }
    
    public static String maskName(String name) {
        if (StringUtils.isEmpty(name)) {
            return name;
        }
        int length = name.length();
        if (length == 1) {
            return name;
        }
        if (length == 2) {
            return name.charAt(0) + "*";
        }
        return name.charAt(0) + maskString(name, 1, length - 1) + name.charAt(length - 1);
    }
}
