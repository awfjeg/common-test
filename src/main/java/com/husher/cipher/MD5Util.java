package com.husher.cipher;


import com.husher.FileUtil;
import org.springframework.util.DigestUtils;

import java.security.MessageDigest;

/**
 * MD5加密工具类
 * @author pibigstar
 *
 */
public class MD5Util {
    /** 盐，用于混交md5 */
    private static final String slat = "&%5123***&&%%$$#@";

    /**
     * 加密
     *
     * @param dataStr str数据
     * @author caiming
     * @date 2022/01/06
     */
    public static String encrypt(String dataStr) {
        try {
            dataStr = dataStr + slat;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes("UTF8"));
            byte s[] = m.digest();
            String result = "";
            for (int i = 0; i < s.length; i++) {
                result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getMD5(String str) {
        String base = str + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    public static void main(String[] args) {
        System.out.println(encrypt(FileUtil.getStringFromFile("D:\\Users\\776216\\Desktop\\A.txt")));
        System.out.println(getMD5(FileUtil.getStringFromFile("D:\\Users\\776216\\Desktop\\aa.txt")));
    }

}
