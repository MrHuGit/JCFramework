package com.android.framework.jc.exception;

import android.support.annotation.StringRes;

import com.android.framework.jc.JcFramework;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/16 18:29
 * @describe 网络错误提示
 * @update
 */
public class NetworkErrorException extends Throwable {

    public NetworkErrorException(String message) {
        super(message);
    }

    public NetworkErrorException(@StringRes int messageRes) {
        super(JcFramework.getInstance().getApplication().getString(messageRes));
    }
}
