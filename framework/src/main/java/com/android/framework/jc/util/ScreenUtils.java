package com.android.framework.jc.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.android.framework.jc.JcFramework;

import java.lang.reflect.Method;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/17 14:25
 * @describe 屏幕相关工具
 * @update
 */
public class ScreenUtils {

    /**
     * 获得屏幕高度(px)
     *
     * @return 屏幕高度 (px)
     */
    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) JcFramework.getInstance()
                .getApplication().getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(displayMetrics);
        }
        return displayMetrics.heightPixels;
    }

    /**
     * 获得屏幕宽度(px)
     *
     * @return 屏幕宽度(px)
     */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) JcFramework.getInstance()
                .getApplication().getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(displayMetrics);
        }
        return displayMetrics.widthPixels;
    }

    /**
     * 获取状态栏高度（px）
     *
     * @return 状态栏高度(px)
     */
    public static int getStatusHeight() {
        int statusHeight = Resources.getSystem().getDimensionPixelSize(
                Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android"));
        if (statusHeight <= 0) {
            try {
                @SuppressLint("PrivateApi") Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                Object object = clazz.newInstance();
                int height = Integer.parseInt(clazz.getField("status_bar_height")
                        .get(object).toString());
                statusHeight = ResourcesUtils.getResource().getDimensionPixelSize(height);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return statusHeight;
    }


    /**
     * 获取虚拟按键的高度（px）
     *
     * @return 虚拟按键的高度（px）
     */
    public static int getNavigationBarHeight() {
        return getFullScreenHeight() - getScreenHeight();
    }

    /**
     * 获取整个屏幕的高度
     *
     * @return 屏幕的高度（包括虚拟按键 px）
     */
    public static int getFullScreenHeight() {
        int result = 0;
        WindowManager windowManager = (WindowManager) JcFramework.getInstance().getApplication().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        Display display = null;
        if (windowManager != null) {
            display = windowManager.getDefaultDisplay();
        }
        DisplayMetrics dm = new DisplayMetrics();
        Class c;
        try {
            c = Class.forName("android.view.Display");
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            result = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
