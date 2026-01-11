package com.content.common.utils;

import java.util.UUID;

public class IdUtils {

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static String uuidNoDash() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String simpleUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static long randomLong() {
        return System.currentTimeMillis();
    }

    public static String randomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    public static String randomNumeric(int length) {
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    public static String randomAlphabetic(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    public static String randomAlphanumeric(int length) {
        return randomString(length);
    }

    public static String snowflakeId() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static String generateId() {
        return uuidNoDash();
    }

    public static String generateShortId() {
        return simpleUUID().substring(0, 16);
    }

    public static String generateOrderId() {
        return DateUtils.now().replace("-", "").replace(":", "").replace(" ", "") + randomNumeric(4);
    }

    public static String generateTradeNo() {
        return "T" + DateUtils.now().replace("-", "").replace(":", "").replace(" ", "") + randomNumeric(6);
    }

    public static String generateRefundNo() {
        return "R" + DateUtils.now().replace("-", "").replace(":", "").replace(" ", "") + randomNumeric(6);
    }

    public static String generateBatchNo() {
        return "B" + DateUtils.now().replace("-", "").replace(":", "").replace(" ", "") + randomNumeric(6);
    }

    public static String generateSerialNo() {
        return "S" + DateUtils.now().replace("-", "").replace(":", "").replace(" ", "") + randomNumeric(6);
    }

    public static String generateVerifyCode(int length) {
        return randomNumeric(length);
    }

    public static String generateCaptcha() {
        return randomNumeric(4);
    }

    public static String generateToken() {
        return uuidNoDash();
    }

    public static String generateSessionId() {
        return uuidNoDash();
    }

    public static String generateNonce() {
        return uuidNoDash();
    }

    public static String generateRequestId() {
        return uuidNoDash();
    }

    public static String generateTraceId() {
        return uuidNoDash();
    }

    public static String generateLogId() {
        return uuidNoDash();
    }

    public static String generateTaskId() {
        return "TASK" + DateUtils.now().replace("-", "").replace(":", "").replace(" ", "") + randomNumeric(4);
    }

    public static String generateJobId() {
        return "JOB" + DateUtils.now().replace("-", "").replace(":", "").replace(" ", "") + randomNumeric(4);
    }

    public static String generateMessageId() {
        return "MSG" + DateUtils.now().replace("-", "").replace(":", "").replace(" ", "") + randomNumeric(6);
    }

    public static String generateEventId() {
        return "EVT" + DateUtils.now().replace("-", "").replace(":", "").replace(" ", "") + randomNumeric(6);
    }

    public static String generateFileId() {
        return "FILE" + DateUtils.now().replace("-", "").replace(":", "").replace(" ", "") + randomNumeric(6);
    }

    public static String generateImageId() {
        return "IMG" + DateUtils.now().replace("-", "").replace(":", "").replace(" ", "") + randomNumeric(6);
    }

    public static String generateVideoId() {
        return "VID" + DateUtils.now().replace("-", "").replace(":", "").replace(" ", "") + randomNumeric(6);
    }

    public static String generateAudioId() {
        return "AUD" + DateUtils.now().replace("-", "").replace(":", "").replace(" ", "") + randomNumeric(6);
    }

    public static String generateDocumentId() {
        return "DOC" + DateUtils.now().replace("-", "").replace(":", "").replace(" ", "") + randomNumeric(6);
    }
}
