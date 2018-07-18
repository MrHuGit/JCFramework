package com.android.framework.jc.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.android.framework.jc.exception.StopInstantiatedException;

/**
 * @author Mr.Hu(Jc)JCFramework
 * @create 2018/3/9 14:25
 * @describe
 * @update
 */

public final class PackageUtils {
    private PackageUtils(){throw new StopInstantiatedException();}
    public static PackageInfo getPackageInfo(Context context){
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo=null;
        try {
             packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }
}
