package com.android.framework.jc.util;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.DisplayMetrics;

import com.android.framework.jc.JcFramework;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/17 13:59
 * @describe Resources相关工具
 * @update
 */
public class ResourcesUtils {
    public static DisplayMetrics getDisplayMetrics() {
        return getResource().getDisplayMetrics();
    }

    /**
     * 获取 Resources
     *
     * @return Resources
     */
    public static Resources getResource() {
        Activity activity = JcFramework.getInstance().getTopActivity();
        return activity == null ? JcFramework.getInstance().getApplication().getResources() : activity.getResources();
    }

    /**
     * 通过字符串id获取字符串
     *
     * @param stringRes
     *         字符串id
     *
     * @return 获取字符串
     */
    public static String getString(@StringRes int stringRes) {
        return getResource().getString(stringRes);
    }


    public static @ColorInt
    int getColor(@ColorRes int colorId, @Nullable Resources.Theme theme) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getResource().getColor(colorId, theme);
        }
        return getColor(colorId);
    }


    public static @ColorInt
    int getColor(@ColorRes int colorId) {
        return getResource().getColor(colorId);
    }
}
