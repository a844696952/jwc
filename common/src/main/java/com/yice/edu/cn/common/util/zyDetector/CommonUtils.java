package com.yice.edu.cn.common.util.zyDetector;

import org.apache.http.client.utils.DateUtils;

import java.util.Date;
import java.util.Random;

/**
 * 流水号工具类
 */
public abstract class CommonUtils {

    private static final Random rd = new Random();
    private static final String INT = "0123456789";
    private static final String STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String ALL = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public interface DATE_PATTERN {
        String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    }

    /**
     * 私有构造器
     **/
    private CommonUtils() {
    }

    /**
     * @param length
     * @return
     * @description 生成一个A-Z，a-z之间的随机字符串，如oUCMpeLZ
     */
    public static String randomStr(int length) {
        return random(length, RndType.STRING);
    }

    /**
     * @param length
     * @return
     * @description 生成一个0-9之间的随机字符串，如1435455512
     */
    public static String randomInt(int length) {
        return random(length, RndType.INT);
    }

    /**
     * @param length
     * @return
     * @description 生成一个0-9,A-Z,a-z之间的随机字符串，如9cWUNAd
     */
    public static String randomAll(int length) {
        return random(length, RndType.ALL);
    }

    /**
     * @param length
     * @return
     * @description 返回指定随机数的流水号，格式为前缀+时间戳+指定位数随机数
     */
    public static String getTransNum(String prefix, int length) {
        StringBuilder stb = new StringBuilder(prefix == null ? "" : prefix);
        String date2String = DateUtils.formatDate(new Date(), DATE_PATTERN.YYYYMMDDHHMMSS);
        stb.append(date2String).append(randomInt(length));
        return stb.toString();
    }

    /**
     * @param length
     * @return
     * @description 返回指定随机数的流水号，格式为时间戳加指定位数随机数
     */
    public static String getTransNum(int length) {
        String date2String = DateUtils.formatDate(new Date(), DATE_PATTERN.YYYYMMDDHHMMSS);
        StringBuilder stb = new StringBuilder(date2String);
        stb.append(randomInt(length));
        return stb.toString();
    }

    /**
     * @param length，随机数的长度，随机数的种类，如数字，字母，数字字母混合all
     * @param rndType
     * @return
     */
    private static String random(int length, RndType rndType) {
        StringBuilder s = new StringBuilder();
        char num;
        for (int i = 0; i < length; i++) {
            if (rndType.equals(RndType.INT))
                num = INT.charAt(rd.nextInt(INT.length()));// 产生数字0-9的随机数
            else if (rndType.equals(RndType.STRING))
                num = STR.charAt(rd.nextInt(STR.length()));// 产生字母a-z的随机数
            else {
                num = ALL.charAt(rd.nextInt(ALL.length()));
            }
            s.append(num);
        }
        return s.toString();
    }

    public enum RndType {
        INT, STRING, ALL
    }
}
