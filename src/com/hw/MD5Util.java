package com.hw;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * MD5 加密工具类
 * 数据库中的密码统一存储为 MD5 摘要，不保存明文
 */
public class MD5Util {

    public static String md5(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                String hex = Integer.toHexString(b & 0xFF);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("MD5 加密失败", e);
        }
    }
}
