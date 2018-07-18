package com.android.framework.jc.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.android.framework.jc.JcFramework;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/17 13:59
 * @describe
 * @update
 */
public class ResourcesUtils {
    public static DisplayMetrics getDisplayMetrics(){
        return getResource().getDisplayMetrics();
    }

    public static Resources getResource(){
        return JcFramework.getInstance().getApplication().getResources();
    }
}
