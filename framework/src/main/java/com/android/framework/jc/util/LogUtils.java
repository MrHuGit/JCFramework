package com.android.framework.jc.util;

import android.util.Log;

import com.android.framework.jc.ConfigManager;
import com.android.framework.jc.JcFramework;
import com.android.framework.jc.exception.StopInstantiatedException;

/**
 * @author Mr.Hu(Jc)JCFramework
 * @create 2018/3/9 16:05
 * @describe
 * @update
 */

public final class LogUtils {
    private LogUtils() {
        throw new StopInstantiatedException();
    }

    private static class Holder {
        private final static boolean DEBUG = "true".equalsIgnoreCase(ConfigManager.getInstance().getValue("logDebug").trim());
    }

    public static void v(String... s) {
        if (s != null && Holder.DEBUG) {
            v(JcFramework.class, StringUtils.buildString(s));
        }
    }

    public static void d(String... s) {
        if (s != null && Holder.DEBUG) {
            d(JcFramework.class, StringUtils.buildString(s));
        }
    }

    public static void i(String... s) {
        if (s != null && Holder.DEBUG) {
            i(JcFramework.class, StringUtils.buildString(s));
        }
    }

    public static void w(String... s) {
        if (s != null && Holder.DEBUG) {
            w(JcFramework.class, StringUtils.buildString(s));
        }
    }

    public static void e(String... s) {
        if (s != null && Holder.DEBUG) {
            e(JcFramework.class, StringUtils.buildString(s));
        }
    }

    public static void v(Class tClass, String... s) {
        if (s != null && Holder.DEBUG) {
            Log.v(tClass.getSimpleName(), StringUtils.buildString(s));
        }
    }

    public static void d(Class tClass, String... s) {
        if (s != null && Holder.DEBUG) {
            Log.d(tClass.getSimpleName(), StringUtils.buildString(s));
        }
    }

    public static void i(Class tClass, String... s) {
        if (s != null && Holder.DEBUG) {
            Log.i(tClass.getSimpleName(), StringUtils.buildString(s));
        }
    }

    public static void w(Class tClass, String... s) {
        if (s != null && Holder.DEBUG) {
            Log.w(tClass.getSimpleName(), StringUtils.buildString(s));
        }
    }

    public static void e(Class tClass, String... s) {
        if (s != null && Holder.DEBUG) {
            Log.e(tClass.getSimpleName(), StringUtils.buildString(s));
        }
    }

}
