package com.android.framework.jc.exception;

import android.support.annotation.StringRes;

import com.android.framework.jc.JcFramework;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/16 18:29
 * @describe
 * @update
 */
public class MessageException extends Throwable{

    public MessageException(String message){
        super(message);
    }

    public MessageException(@StringRes int messageRes){
        super(JcFramework.getInstance().getApplication().getString(messageRes));
    }
}
