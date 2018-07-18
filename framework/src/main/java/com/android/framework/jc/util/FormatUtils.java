package com.android.framework.jc.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/17 10:53
 * @describe 解析工具
 * @update
 */
public class FormatUtils {
    private static class Holder {
        private final static DecimalFormat DECIMAL_FORMAT = new DecimalFormat();
        private final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    /**
     * 解析字符串为double
     * @param value double字符串
     * @return double
     */
    public static double parseDouble(String value) {
        double result = 0;
        try {
            result = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 解析字符串为double
     * @param value double字符串
     * @return double字符串
     */
    public static String parseDouble2(String value) {
        return parseDouble(value,"#0.00");
    }
    /**
     * 根据指定小数位数解析字符串为double
     * @param value double字符串
     * @param pattern "##.##"
     * @return double字符串
     */
    public static String parseDouble(String value,String pattern) {
        DecimalFormat decimalFormat= Holder.DECIMAL_FORMAT;
        decimalFormat.applyPattern(pattern);
        return decimalFormat.format(parseDouble(value));
    }
    /**
     * 解析字符串为int
     * @param value int字符串
     * @return int
     */
    public static int parseInt(String value) {
        int result = 0;
        try {
            result = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解析字符串为float
     * @param value float字符串
     * @return float
     */
    public static float parseFloat(String value) {
        float result = 0;
        try {
            result = Float.parseFloat(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解析字符串为long
     * @param value long字符串
     * @return long
     */
    public static long parseLong(String value) {
        long result = 0;
        try {
            result = Long.parseLong(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 解析时间（标准格式）
     * @param time 时间字符串（时间戳）
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String parseTime(String time) {
        return parseTime(parseLong(time));
    }

    /**
     * 解析时间（标准格式）
     * @param time 时间戳
     * @return 标准格式字符串 yyyy-MM-dd HH:mm:ss
     */
    public static String parseTime(long time) {
        SimpleDateFormat format = Holder.SIMPLE_DATE_FORMAT;
        format.applyPattern("yyyy-MM-dd HH:mm:ss");
        return format.format(time);
    }

    /**
     * 根据传入的格式解析时间
     * @param time 时间戳
     * @param pattern 指定格式
     * @return 根据指定格式返回时间格式
     */
    public static String parseTime(String time ,String pattern) {
        return parseTime(parseLong(time), pattern);
    }

    /**
     * 根据传入的格式解析时间
     * @param time 时间戳
     * @param pattern 指定格式
     * @return 根据指定格式返回时间格式
     */
    public static String parseTime(long time ,String pattern) {
        SimpleDateFormat format = Holder.SIMPLE_DATE_FORMAT;
        format.applyPattern(pattern);
        return format.format(time);
    }


}
