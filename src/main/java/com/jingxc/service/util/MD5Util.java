package com.jingxc.service.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    // 全局数组
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f" };

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    public static String getMD5(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

    public static void main(String[] args) {
        System.out.println(getMD5("fangming"));
    }

    // 先求两个数的最大公约数（使用辗转相除法）
    public static int getMaxCommonDivisor(int a, int b) {
        // 定义一个交换站值
        int temp = 0;
        while (a % b != 0) {
            temp = a % b;
            a = b;
            b = temp;
        }
        return b;
    }

    // 求两个数的最小公倍数（两个数相乘 等于 这两个数的最大公约数和最小公倍数的 积）
    public static int getMinCommonMultiple(int a, int b) {
        return a * b / getMaxCommonDivisor(a, b);
    }

    // 求多个数的最小公倍数
    public static int getMinMultiCommonMultiple(Integer[] arrays) {
        int val = arrays[0];
        // 实现原理：拿前两个数的最小公约数和后一个数比较，求他们的公约数以此来推。。。
        for (int i = 1; i < arrays.length; i++) {
            val = getMinCommonMultiple(val, arrays[i]);
        }
        return val;
    }

}
