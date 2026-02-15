package com.content.common.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.mindrot.jbcrypt.BCrypt;

public class CustomDigestUtils {

    public static String md5Hex(String str) {
        return DigestUtils.md5Hex(str);
    }

    public static String sha1Hex(String str) {
        return DigestUtils.sha1Hex(str);
    }

    public static String sha256Hex(String str) {
        return DigestUtils.sha256Hex(str);
    }

    public static String sha512Hex(String str) {
        return DigestUtils.sha512Hex(str);
    }

    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }

    public static String sha1(String str) {
        return DigestUtils.sha1Hex(str);
    }

    public static String sha256(String str) {
        return DigestUtils.sha256Hex(str);
    }

    public static String sha512(String str) {
        return DigestUtils.sha512Hex(str);
    }

    public static String bcrypt(String str) {
        return BCrypt.hashpw(str, BCrypt.gensalt());
    }

    public static String bcrypt(String str, String salt) {
        return BCrypt.hashpw(str, salt);
    }

    public static boolean checkBcrypt(String str, String hashed) {
        return BCrypt.checkpw(str, hashed);
    }

    public static String generateSalt() {
        return BCrypt.gensalt();
    }

    public static String generateSalt(int strength) {
        return BCrypt.gensalt(strength);
    }
}
