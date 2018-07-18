package com.android.framework.jc.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.android.framework.jc.exception.MessageException;

import java.net.SocketTimeoutException;

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

    public static void toast(Context context, Throwable throwable) {
        if (throwable instanceof MessageException) {
            toast(context, throwable.getMessage());
        } else if (throwable instanceof SocketTimeoutException) {
            toast(context, "请求超时");
        } else {
            toast(context, "网络异常");
        }
    }
}
