package com.android.framework.jc.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.android.framework.jc.R;
import com.android.framework.jc.exception.NetworkErrorException;

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
        if (throwable instanceof NetworkErrorException) {
            toast(context, throwable.getMessage());
        } else if (throwable instanceof SocketTimeoutException) {
            toast(context, R.string.network_timeout);
        } else {
            toast(context, R.string.network_error);
        }
    }
}
