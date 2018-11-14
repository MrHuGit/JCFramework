package com.android.framework.jc.util;

import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.util.DisplayMetrics;

import com.android.framework.jc.JcFramework;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/17 13:59
 * @describe
 * @update
 */
public class ResourcesUtils {
    public static DisplayMetrics getDisplayMetrics() {
        return getResource().getDisplayMetrics();
    }

    public static Resources getResource() {
        Activity activity = JcFramework.getInstance().getTopActivity();
        return activity == null ? JcFramework.getInstance().getApplication().getResources() : activity.getResources();
    }

    public static String getString(@StringRes int stringRes) {
        return getResource().getString(stringRes);
    }
}
