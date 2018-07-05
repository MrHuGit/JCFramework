package com.android.jc_framework.utils;

import android.util.Log;

import com.android.jc_framework.exception.StopInstantiatedException;

/**
 * @author Mr.Hu(Jc)
 * @create 2018/3/9 16:05
 * @describe
 * @update
 */

public final class LogUtils {
    private LogUtils(){throw new StopInstantiatedException();}
    private static String TAG="";
    private static boolean DEBUG=true;

    public void v(String ...s){
        if (s!=null&&DEBUG){
            Log.v(TAG, StringUtils.buildString(s));
        }
    }
    public void d(String ...s){
        if (s!=null&&DEBUG){
            Log.d(TAG, StringUtils.buildString(s));
        }
    }
    public void i(String ...s){
        if (s!=null&&DEBUG){
            Log.i(TAG, StringUtils.buildString(s));
        }
    }
    public void w(String ...s){
        if (s!=null&&DEBUG){
            Log.w(TAG, StringUtils.buildString(s));
        }
    }
    public void e(String ...s){
        if (s!=null&&DEBUG){
            Log.e(TAG, StringUtils.buildString(s));
        }
    }
}
