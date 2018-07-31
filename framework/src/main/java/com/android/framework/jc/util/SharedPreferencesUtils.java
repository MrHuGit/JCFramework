package com.android.framework.jc.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.android.framework.jc.JcFramework;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/31 10:48
 * @describe SharedPreferences工具类
 * @update
 */
public class SharedPreferencesUtils {

    private static class Holder {
        private final static String SP_NAME = JcFramework.getInstance().getApplication().getPackageName();
        private final static SharedPreferences SHARED_PREFERENCES = JcFramework.getInstance().getApplication().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);

    }

    /**
     * 保存String
     *
     * @param key
     *         key
     * @param value
     *         value
     */
    public static void putString(String key, String value) {
        if (!TextUtils.isEmpty(key)) {
            SharedPreferences sp = Holder.SHARED_PREFERENCES;
            if (sp != null) {
                sp.edit().putString(key, value).apply();
            }
        }
    }

    /**
     * 通过key获取String
     *
     * @param key
     *         key
     *
     * @return String
     */
    public static String getString(String key) {
        String result = "";
        if (!TextUtils.isEmpty(key)) {
            SharedPreferences sp = Holder.SHARED_PREFERENCES;
            if (sp != null) {
                result = sp.getString(key, "");
            }
        }
        return result;
    }

    /**
     * 保存int
     *
     * @param key
     *         key
     * @param value
     *         value
     */
    public static void putInt(String key, int value) {
        if (!TextUtils.isEmpty(key)) {
            SharedPreferences sp = Holder.SHARED_PREFERENCES;
            if (sp != null) {
                sp.edit().putInt(key, value).apply();
            }
        }
    }

    /**
     * 通过key获取int
     *
     * @param key
     *         key
     *
     * @return int
     */
    public static int getInt(String key) {
        int result = 0;
        if (!TextUtils.isEmpty(key)) {
            SharedPreferences sp = Holder.SHARED_PREFERENCES;
            if (sp != null) {
                result = sp.getInt(key, 0);
            }
        }
        return result;
    }

    /**
     * 保存long
     *
     * @param key
     *         key
     * @param value
     *         value
     */
    public static void putLong(String key, long value) {
        if (!TextUtils.isEmpty(key)) {
            SharedPreferences sp = Holder.SHARED_PREFERENCES;
            if (sp != null) {
                sp.edit().putLong(key, value).apply();
            }
        }
    }

    /**
     * 通过key获取long
     *
     * @param key
     *         key
     *
     * @return long
     */
    public static long getLong(String key) {
        long result = 0L;
        if (!TextUtils.isEmpty(key)) {
            SharedPreferences sp = Holder.SHARED_PREFERENCES;
            if (sp != null) {
                result = sp.getLong(key, 0L);
            }
        }
        return result;
    }

    /**
     * 保存boolean
     *
     * @param key
     *         key
     * @param value
     *         value
     */
    public static void putFloat(String key, float value) {
        if (!TextUtils.isEmpty(key)) {
            SharedPreferences sp = Holder.SHARED_PREFERENCES;
            if (sp != null) {
                sp.edit().putFloat(key, value).apply();
            }
        }
    }

    /**
     * 通过key获取float
     *
     * @param key
     *         key
     *
     * @return float
     */
    public static float getFloat(String key) {
        float result = 0f;
        if (!TextUtils.isEmpty(key)) {
            SharedPreferences sp = Holder.SHARED_PREFERENCES;
            if (sp != null) {
                result = sp.getFloat(key, 0f);
            }
        }
        return result;
    }

    /**
     * 保存boolean
     *
     * @param key
     *         key
     * @param value
     *         value
     */
    public static void putBoolean(String key, boolean value) {
        if (!TextUtils.isEmpty(key)) {
            SharedPreferences sp = Holder.SHARED_PREFERENCES;
            if (sp != null) {
                sp.edit().putBoolean(key, value).apply();
            }
        }
    }

    /**
     * 通过key获取boolean
     *
     * @param key
     *         key
     *
     * @return boolean
     */
    public static boolean getBoolean(String key) {
        boolean result = false;
        if (!TextUtils.isEmpty(key)) {
            SharedPreferences sp = Holder.SHARED_PREFERENCES;
            if (sp != null) {
                result = sp.getBoolean(key, false);
            }
        }
        return result;
    }

    /**
     * 保存Set<String>
     *
     * @param key
     *         key
     * @param value
     *         value
     */
    public static void putStringSet(String key, Set<String> value) {
        if (!TextUtils.isEmpty(key) && value != null) {
            SharedPreferences sp = Holder.SHARED_PREFERENCES;
            if (sp != null) {
                sp.edit().putStringSet(key, value).apply();
            }
        }
    }

    /**
     * 通过key获取Set<String>
     *
     * @param key
     *         key
     *
     * @return Set<String>
     */
    public static Set<String> getStringSet(String key, Set<String> value) {
        Set<String> result = null;
        if (!TextUtils.isEmpty(key) && value != null) {
            SharedPreferences sp = Holder.SHARED_PREFERENCES;
            if (sp != null) {
                result = sp.getStringSet(key, new HashSet<>());
            }
        }
        return result == null ? new HashSet<>() : result;
    }

    /**
     * 通过key移除对应保存的数据
     *
     * @param key
     *         key
     */
    public static void remove(String key) {
        if (!TextUtils.isEmpty(key)) {
            SharedPreferences sp = Holder.SHARED_PREFERENCES;
            if (sp != null) {
                sp.edit().remove(key).apply();
            }
        }
    }
}
