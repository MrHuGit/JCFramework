package com.android.framework.jc.util;

import android.text.TextUtils;

import com.android.framework.jc.exception.StopInstantiatedException;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/3/9 16:11
 * @describe 字符串相关工具
 * @update
 */

public final  class StringUtils {
    private StringUtils(){throw new StopInstantiatedException();}
    /**
     * 拼接字符串
     *
     * @param s
     *         目标字符串数组
     *
     * @return 拼接后结果字符串
     */
    public static String buildString(String... s) {
        String result = "";
        if (s != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String childString : s) {
                stringBuilder.append(childString).append(",");
            }
            int length = stringBuilder.length();
            if (length > 0) {
                result = stringBuilder.substring(0, length - 1);
            }
        }
        return result;
    }

    /**
     * 字符串首字母转成大写
     *
     * @param s
     *         需要转换的字符串
     *
     * @return 转换后的字符串
     */
    public static String upperFirstLetter(final String s) {
        String result = "";
        if (TextUtils.isEmpty(s) || !Character.isLowerCase(s.charAt(0))) {
            result = s;
        } else {
            result = String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
        }
        return result;
    }

    /**
     * byte[]数组转换为16进制的字符串
     *
     * @param data 要转换的字节数组
     * @return 转换后的结果
     */
    public static String byteArrayToHexString(byte[] data){
        String result;
        if (data==null){
            result=null;
        }else{
            StringBuilder stringBuilder=new StringBuilder(data.length*2);
            for (byte b:data){
                int v = b & 0xff;
                if (v < 16) {
                    stringBuilder.append('0');
                }
                stringBuilder.append(Integer.toHexString(v));
            }
            result=stringBuilder.toString();
        }


        return result;
    }

    /**
     * 16进制表示的字符串转换为字节数组
     *
     * @param s 16进制表示的字符串
     * @return byte[] 字节数组
     */
    public static byte[] hexStringToByteArray(String s) {
        byte[] result;
        if (TextUtils.isEmpty(s)){
            result=null;
        }else{
            int len = s.length();
            result = new byte[len / 2];
            for (int i = 0; i < len; i += 2) {
                // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
                result[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
                        .digit(s.charAt(i + 1), 16));
            }
        }

        return result;
    }
}
