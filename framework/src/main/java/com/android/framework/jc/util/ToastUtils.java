package com.android.framework.jc.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/17 10:44
 * @describe toast相关工具
 * @update
 */
public class ToastUtils {
    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void toast(Context context, @StringRes int messageRes) {
        Toast.makeText(context, messageRes, Toast.LENGTH_SHORT).show();
    }

}
