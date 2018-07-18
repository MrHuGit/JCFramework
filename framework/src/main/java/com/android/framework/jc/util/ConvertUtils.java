package com.android.framework.jc.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/17 13:55
 * @describe 转换工具
 * @update
 */
public class ConvertUtils {


    /**
     * sp->px
     *
     * @param spValue
     *         sp
     *
     * @return px
     */
    public static int sp2px(final float spValue) {
        return (int) (spValue * ResourcesUtils.getDisplayMetrics().scaledDensity + 0.5f);
    }

    /**
     * dp->px
     *
     * @param dpValue
     *         dp
     *
     * @return px
     */
    public static int dp2px(final float dpValue) {
        return (int) (dpValue * ResourcesUtils.getDisplayMetrics().density + 0.5f);
    }

    /**
     * px->dp
     *
     * @param pxValue
     *         px
     *
     * @return dp
     */
    public static int px2dp(final float pxValue) {
        return (int) (pxValue / ResourcesUtils.getDisplayMetrics().density + 0.5f);
    }

    /**
     * px->sp
     *
     * @param pxValue
     *         px
     *
     * @return sp
     */
    public static int px2sp(final float pxValue) {
        return (int) (pxValue / ResourcesUtils.getDisplayMetrics().scaledDensity + 0.5f);
    }

    /**
     * bitmap->bytes
     *
     * @param bitmap
     *         bitmap
     * @param format
     *         format
     *
     * @return bytes
     */
    public static byte[] bitmap2Bytes(final Bitmap bitmap, final Bitmap.CompressFormat format) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(format, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * bytes -> bitmap
     *
     * @param bytes
     *         bytes
     *
     * @return bitmap
     */
    public static Bitmap bytes2Bitmap(final byte[] bytes) {
        return (bytes == null || bytes.length == 0) ? null : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
